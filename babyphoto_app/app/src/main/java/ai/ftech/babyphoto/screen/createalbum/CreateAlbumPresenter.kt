package ai.ftech.babyphoto.screen.createalbum

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Data
import ai.ftech.babyphoto.screen.createalbum.relation.DialogRelationFragment
import ai.ftech.babyphoto.screen.home.HomeActivity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.Gravity
import android.view.Window
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*


class CreateAlbumPresenter(activity: CreateAlbumActivity) : ICreateAlbum {
    private val view = activity
    lateinit var file: File
    private lateinit var file_path: String
    private lateinit var strGender: String
    private lateinit var requestBody: RequestBody
    private lateinit var singleFile: MultipartBody.Part
    private lateinit var rqIdaccount: RequestBody
    private lateinit var rqName: RequestBody
    private lateinit var rqGender: RequestBody
    private lateinit var rqBirthday: RequestBody
    private lateinit var rqRelation: RequestBody
    private var select: Int = 1
    lateinit var path: String

    override fun getGenderAlbum(): Int {
        view.ivBoy.setOnClickListener {
            view.ivBoy.setBackgroundResource(R.drawable.shape_cir_yellow_bg_corner_large)
            view.ivGirl.setBackgroundResource(R.drawable.shape_cir_grey_bg_corner_90)
            select = 1
        }
        view.ivGirl.setOnClickListener {
            view.ivGirl.setBackgroundResource(R.drawable.shape_cir_yellow_bg_corner_large)
            view.ivBoy.setBackgroundResource(R.drawable.shape_cir_grey_bg_corner_90)
            select = 0
        }
        return select
    }


    override fun getBirthdayAlbum(tvBirthday: TextView) {

        view.flBirthday.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

            val datePickerDialog =
                DatePickerDialog(view, object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(
                        view: DatePicker?,
                        year: Int,
                        month: Int,
                        dayOfMonth: Int
                    ) {
                        tvBirthday.text = "${dayOfMonth}/${month + 1}/${year}"
                    }
                }, year, month, dayOfMonth)
            datePickerDialog.show()
        }

    }

    override fun getRelationAlbum(relation: String) {
        view.flRelation.setOnClickListener {
            val dialogRelationFragment = DialogRelationFragment()
            dialogRelationFragment.show(view.supportFragmentManager, dialogRelationFragment.tag)
        }
        view.tvRelation.text = relation
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun createAlbum(pathImage: String) {
        view.btnCreate.setOnClickListener {
            val progressdialog = ProgressDialog(view, R.style.AppCompatAlertDialogStyle)
            progressdialog.setMessage("Updating")
            progressdialog.setCancelable(false)
            progressdialog.show()

            sendSingleImage(pathImage)
            if (singleFile != null && rqIdaccount != null && rqName != null && rqGender != null && rqBirthday != null && rqRelation != null) {
                val dataService = APIService.base()
                val callback = dataService.albumInsert(
                    singleFile,
                    rqIdaccount,
                    rqName,
                    rqGender,
                    rqBirthday,
                    rqRelation
                )

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
    }

    fun sendSingleImage(pathImage: String) {
        file = File(pathImage)
        file_path = file.absolutePath + System.currentTimeMillis() + ".png"
        requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        singleFile = MultipartBody.Part.createFormData("file", file_path, requestBody)


        val name = view.edtName.text.toString()
        val gender = getGenderAlbum()
        strGender = if (gender == 1) {
            "1"
        } else {
            "0"
        }
        val birthday: String = view.tvBirthday.text.toString()
        val relation: String = view.tvRelation.text.toString()

        rqIdaccount = RequestBody.create(MediaType.parse("multipart/form-data"), "1")
        rqName = RequestBody.create(MediaType.parse("multipart/form-data"), name)
        rqGender = RequestBody.create(MediaType.parse("multipart/form-data"), strGender)
        rqBirthday = RequestBody.create(MediaType.parse("multipart/form-data"), birthday)
        rqRelation = RequestBody.create(MediaType.parse("multipart/form-data"), relation)
    }


    fun openBackDialog() {
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

    fun getRealPathFromUri(contentUri: Uri): String {
        var projection = arrayOf(
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

}

