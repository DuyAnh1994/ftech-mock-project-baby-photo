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
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ListImagePresenter(activity: ListImageActivity) {
    private val view = activity
    private var arrayImage: MutableList<String> = ArrayList()
    private var arrayCb: MutableList<Boolean> = ArrayList()
    private var count: Int = 0
    lateinit var idalbum: RequestBody
    lateinit var description: RequestBody
    lateinit var timeline: RequestBody
    lateinit var multiFile: MultipartBody.Part
    lateinit var path: String
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
        val projection = arrayOf(
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
            val files: MutableList<MultipartBody.Part> = ArrayList()
            progressdialog.setMessage("Updating")
            progressdialog.setCancelable(false)
            progressdialog.show()
            for (position in 0 until arrayImage.size) {
                if (arrayCb[position]) {
                    convertToFile(arrayImage[position])
                    files.add(multiFile)
                }
            }
            if (files.size > 0) {
                addMultiImageToServer(files)
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addMultiImageToServer(files: MutableList<MultipartBody.Part>) {
        if (files.size > 0) {

            getInfoImage()

            val dataService = APIService.base()
            val callback = dataService.imageInsertMulti(files, idalbum, description, timeline)
            callback.enqueue(object : Callback<Data<String>> {
                override fun onResponse(
                    call: Call<Data<String>>,
                    response: Response<Data<String>>
                ) {

                    if (response.body()!!.code == "code13") {
                        Toast.makeText(view, response.body()!!.msg, Toast.LENGTH_SHORT).show()
                        val intent = Intent(view, HomeActivity::class.java)
                        intent.putExtra("idalbum", "1")
                        view.startActivity(intent)
                        progressdialog.dismiss()
                    } else {
                        Toast.makeText(view, response.body()!!.msg, Toast.LENGTH_SHORT).show()
                        progressdialog.dismiss()
                    }
                }

                override fun onFailure(call: Call<Data<String>>, t: Throwable) {
                    Toast.makeText(view, t.message, Toast.LENGTH_SHORT).show()
                    progressdialog.dismiss()
                }
            })
        }
    }

    fun convertToFile(linkImage: String) {
        val file = File(linkImage)
        val file_path: String = file.absolutePath + System.currentTimeMillis() + ".png"

        val requestBody: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), file)
        multiFile = MultipartBody.Part.createFormData("file[]", file_path, requestBody)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getInfoImage() {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatted: String = current.format(formatter)

        idalbum = RequestBody.create(MediaType.parse("multipart/form-data"), "1")
        description = RequestBody.create(MediaType.parse("multipart/form-data"), "Hello")
        timeline = RequestBody.create(MediaType.parse("multipart/form-data"), formatted)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun addImageSingleToServer(uri: Uri) {
        progressdialog.setMessage("Updating")
        progressdialog.setCancelable(false)
        progressdialog.show()

        val image_path = getRealPathFromUri(uri)
        val file = File(image_path)
        val file_path: String = file.absolutePath + System.currentTimeMillis() + ".png"

        val requestBody: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val singFile = MultipartBody.Part.createFormData("file", file_path, requestBody)

        getInfoImage()
        val dataService = APIService.base()
        val callback = dataService.imageInsertSingle(singFile, idalbum, description, timeline)
        callback.enqueue(object : Callback<Data<String>> {
            override fun onResponse(
                call: Call<Data<String>>,
                response: Response<Data<String>>
            ) {

                if (response.body()!!.code == "code13") {
                    Toast.makeText(view, response.body()!!.msg, Toast.LENGTH_SHORT).show()
                    val intent = Intent(view, HomeActivity::class.java)
                    intent.putExtra("idalbum", "1")
                    view.startActivity(intent)
                    progressdialog.dismiss()
                } else {
                    Toast.makeText(view, response.body()!!.msg, Toast.LENGTH_SHORT).show()
                    progressdialog.dismiss()
                }
            }

            override fun onFailure(call: Call<Data<String>>, t: Throwable) {
                Toast.makeText(view, t.message, Toast.LENGTH_SHORT).show()
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

    fun getRealPathFromUri(contentUri: Uri): String {
        val projection = arrayOf(
            MediaStore.Images.ImageColumns.DATA,
        )

        val cursor = view.contentResolver.query(
            contentUri, projection, null, null, null
        )
        if (cursor!!.moveToFirst()) {
            path = cursor.getString(0)
        }
        cursor.close()
        return path
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