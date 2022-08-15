package ai.ftech.babyphoto.screen.listimage

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Data
import ai.ftech.babyphoto.screen.home.HomeActivity
import android.Manifest
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.Gravity
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random


class ListImagePresenter(activity: ListImageActivity) {
    private val view = activity
    private var arrayImage: MutableList<String> = ArrayList()
    private var arrayCb: MutableList<Boolean> = ArrayList()
    private var count: Int = 0
    var arrayError: MutableList<Int> = ArrayList()
    var progressdialog = ProgressDialog(view, R.style.AppCompatAlertDialogStyle)

    // xin quyền truy cập thư viện
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(view, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                view,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 998
            )
        }
        getImage()
    }

    //lấy ảnh từ thư viện để hiển thị ra 1 danh sách
    @RequiresApi(Build.VERSION_CODES.O)
    fun getImage() {
        var projection = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATA,
        )

        val cursor = view.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection, null, null, null
        )
        if (cursor!!.count > 0) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val data: String =
                    cursor.getString(2)
                arrayImage.add(data)
                arrayCb.add(false)
                cursor.moveToNext()
            }
            cursor.close()
        }
        val gridLayoutManager = GridLayoutManager(view, 3)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        view.rvImageView.layoutManager = gridLayoutManager
        val adapter = ListImageAdapter(view, arrayImage, arrayCb, object : IListImage {
            override fun setCamera() {
                checkPermissionCamera()
            }

            override fun setImage(position: Int, select: Boolean) {
                arrayCb[position] = select
                if (select) {
                    count++
                    view.tvTitle.text = "Select (${count})"
                    view.btnAdd.setBackgroundResource(R.drawable.shape_orange_bg_corner_20)
                } else {
                    count--
                    if (count > 0) {
                        view.tvTitle.text = "Select (${count})"
                        view.btnAdd.setBackgroundResource(R.drawable.shape_orange_bg_corner_20)
                    } else {
                        view.tvTitle.text = "Add Moment"
                        view.btnAdd.setBackgroundResource(R.drawable.shape_gray_bg_corner_20)
                    }
                }
            }
        })
        view.rvImageView.adapter = adapter
        addImage()
    }

    //check quyền camera
    fun checkPermissionCamera() {
        if (ContextCompat.checkSelfPermission(view, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                view,
                arrayOf(Manifest.permission.CAMERA), 999
            )
        }
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        view.activityResultLauncher.launch(intent)
    }

    //Đẩy dữ liệu ảnh lên server
    @RequiresApi(Build.VERSION_CODES.O)
    fun addImage() {
        view.btnAdd.setOnClickListener {
            progressdialog.setMessage("Updating")
            progressdialog.setCancelable(false)
            progressdialog.show()
            for (position in 0 until arrayImage.size) {
                if (arrayCb[position]) {
                    sendImageToFirebase(arrayImage[position], position + 1)
                }
            }
        }
//            for (i in 0 until arrayError.size) {
//                Toast.makeText(
//                    view,
//                    "image ${arrayError[i]} add no successfully ",
//                    Toast.LENGTH_SHORT
//                ).show()
//                progressdialog.dismiss()
//            }
//        }
//        Toast.makeText(
//            view,
//            "create album successfully",
//            Toast.LENGTH_SHORT
//        ).show()
//        val intent = Intent(view, HomeActivity::class.java)
//        view.startActivity(intent)
//        progressdialog.dismiss()
    }

    // gửi ảnh từ thư viện lên server
    @RequiresApi(Build.VERSION_CODES.O)
    fun sendImageToFirebase(dataImage: String, position: Int) {
        var storageReference: StorageReference =
            FirebaseStorage.getInstance().getReferenceFromUrl("gs://baby-photo-fb591.appspot.com")

        val fileReference: StorageReference = storageReference.child(
            System.currentTimeMillis().toString() + ".png"
        )
        val FILE = Uri.fromFile(File(dataImage))
        val uploadTask = fileReference.putFile(FILE)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            fileReference.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result.toString()
                postServer(downloadUri, position, arrayError)
            } else {
                Toast.makeText(view, "Fail, please retry", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            Toast.makeText(view, "Fail, please retry", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun postServer(bsImage: String, position: Int, arrayError: MutableList<Int>) {
        //lấy thời gian hiện tại
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatted: String = current.format(formatter)

        val dataService = APIService.base()
        val callback =
            dataService.imageInsert( 123, bsImage, "hello", formatted)
        callback.enqueue(object : Callback<Data<String>> {
            override fun onResponse(
                call: Call<Data<String>>,
                response: Response<Data<String>>
            ) {
                if (response.body()!!.code == "code13") {
//                    Toast.makeText(
//                        view,
//                        "create album successfully",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    progressdialog.dismiss()
                } else {
                    arrayError.add(position)
//                    val intent = Intent(view, HomeActivity::class.java)
//                    view.startActivity(intent)
//                    Toast.makeText(
//                        view,
//                        response.body()!!.msg,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    progressdialog.dismiss()
                }
            }

            override fun onFailure(call: Call<Data<String>>, t: Throwable) {
                arrayError.add(position)
//                Toast.makeText(
//                    view,
//                    t.message,
//                    Toast.LENGTH_SHORT
//                ).show()
//                progressdialog.dismiss()
            }

        })

    }


    // gửi ảnh từ camera lên server
    @RequiresApi(Build.VERSION_CODES.O)
    fun sendImageToFirebase(uri: Uri) {
        progressdialog.setMessage("Updating")
        progressdialog.setCancelable(false)
        progressdialog.show()
        var storageReference: StorageReference =
            FirebaseStorage.getInstance().getReferenceFromUrl("gs://baby-photo-fb591.appspot.com")

        val fileReference: StorageReference = storageReference.child(
            System.currentTimeMillis().toString() + ".png"
        )

        val uploadTask = fileReference.putFile(uri)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            fileReference.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result.toString()
                postServer(downloadUri)
            } else {
                Toast.makeText(view, "Fail, please retry", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            Toast.makeText(view, "Fail, please retry", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun postServer(bsImage: String) {
        //random id ảnh
        //lấy thời gian hiện tại
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatted: String = current.format(formatter)

        val dataService = APIService.base()
        val callback =
            dataService.imageInsert(123, bsImage, "hello", formatted)
        callback.enqueue(object : Callback<Data<String>> {
            override fun onResponse(
                call: Call<Data<String>>,
                response: Response<Data<String>>
            ) {
                if (response.body()!!.code == "code13") {
                    Toast.makeText(
                        view,
                        "add image successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(view, HomeActivity::class.java)
                    view.startActivity(intent)
                    progressdialog.dismiss()
                } else {
                    Toast.makeText(
                        view,
                        response.body()!!.msg,
                        Toast.LENGTH_SHORT
                    ).show()
                    progressdialog.dismiss()
                }

            }

            override fun onFailure(call: Call<Data<String>>, t: Throwable) {
                Toast.makeText(
                    view,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
                progressdialog.dismiss()
            }

        })

    }

    fun convertUri(bitmap: Bitmap): Uri {
        //convert bitmap to uri
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(
            view.getContentResolver(),
            bitmap,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    fun cancelCreate() {
        view.ivCancel.setOnClickListener {
            openBackDialog()
        }
    }

    private fun openBackDialog() {
        val dialog = Dialog(view)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_back_create_album_layout)
        val window: Window = dialog.window ?: return
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes = window.attributes
        windowAttributes.gravity = Gravity.CENTER
        window.attributes = windowAttributes
        val btnCancel: Button = dialog.findViewById(R.id.btnDialogBacKCancel)
        val btnOK: Button = dialog.findViewById(R.id.btnDialogBacKOk)
        btnOK.setOnClickListener {
            view.finish()
            dialog.dismiss()
        }
        btnCancel.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

}