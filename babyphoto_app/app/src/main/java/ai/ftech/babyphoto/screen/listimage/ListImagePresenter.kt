package ai.ftech.babyphoto.screen.listimage

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Data
import android.Manifest
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random


class ListImagePresenter(activity: ListImageActivity) {
    private val view = activity
    private var arrayImage: MutableList<String> = ArrayList()
    private var arrayCb: MutableList<Boolean> = ArrayList()
    var arrayError: MutableList<Int> = ArrayList()
    private var count: Int = 0
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
            for (position in 0 until arrayImage.size) {
                if (arrayCb[position]) {
                    // chuyển bitmap về base64
                    val bitmap: Bitmap = BitmapFactory.decodeFile(arrayImage[position])
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                    val b: ByteArray = baos.toByteArray()
                    var bsImage = Base64.getEncoder().encodeToString(b)
                    postServer(bsImage, position)
                }
            }
//            if (arrayError.size == 0) {
//                Toast.makeText(
//                    view,
//                    "create album successfully",
//                    Toast.LENGTH_SHORT
//                ).show()
//                progressdialog.dismiss()
//            } else {
//                for (i in 0 until arrayError.size - 1) {
//                    Toast.makeText(
//                        view,
//                        "image ${arrayError[i]} update no successfully ",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    progressdialog.dismiss()
//                }
//            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun postServer(bsImage: String, position: Int) {
//        progressdialog.setMessage("Updating")
//        progressdialog.setCancelable(false)
//        progressdialog.show()
        //random id ảnh
        val ID_IMAGE: Int = Random.nextInt(1000, 999999999)
        //lấy thời gian hiện tại
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatted: String = current.format(formatter)

        Log.d("BBB", "postServer: ${bsImage}")
//        val dataService = APIService.base()
//        val callback =
//            dataService.imageInsert(ID_IMAGE, 123, bsImage, "hello", formatted)
//        callback.enqueue(object : Callback<Data<String>> {
//            override fun onResponse(
//                call: Call<Data<String>>,
//                response: Response<Data<String>>
//            ) {
//                if (response.body()!!.code == "code13") {
//
//                } else {
//                    arrayError.add(position)
//                }
//
//            }
//
//            override fun onFailure(call: Call<Data<String>>, t: Throwable) {
//                arrayError.add(position)
//            }
//
//        })
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