package ai.ftech.babyphoto.screen.createalbum

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.screen.createalbum.preview.PhotoFolderActivity
import ai.ftech.babyphoto.screen.createalbum.relation.DialogRelationFragment
import ai.ftech.babyphoto.screen.home.HomeActivity
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*


class CreateAlbumActivity : AppCompatActivity(), DialogRelationFragment.ICreateName,
    ICreateContract.ICreateView {
    lateinit var ivBackHome: ImageView
    lateinit var btnCreate: Button
    lateinit var ivAvatar: CircleImageView
    lateinit var edtName: EditText
    lateinit var ivBoy: ImageView
    lateinit var ivGirl: ImageView
    lateinit var tvBirthday: TextView
    lateinit var flBirthday: FrameLayout
    lateinit var tvRelation: TextView
    lateinit var flRelation: FrameLayout
    lateinit var flCamera: FrameLayout
    lateinit var createAlbumPresenter: CreateAlbumPresenter
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var bitmapAvatar: Boolean = false
    var ID_ACCOUNT: Int = 1
    private var select: Int = 1
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
    lateinit var path: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_album_activity)

//        var intent = intent
//        ID_ACCOUNT = intent.getIntExtra("idaccount", 0)
        initView()
        getUriBaby()
        getGenderAlbum()
        getBirthdayAlbum()
        getRelationAlbum()
        changeButton()
        getAvatar(ID_ACCOUNT)
    }

    private fun initView() {
        ivBackHome = findViewById(R.id.ivCreateAlbumBackHome)
        btnCreate = findViewById(R.id.btnCreateAlbumCreate)
        ivAvatar = findViewById(R.id.ivCreateAlbumAvatar)
        edtName = findViewById(R.id.edtCreateAlbumName)
        ivBoy = findViewById(R.id.ivCreateAlbumBoy)
        ivGirl = findViewById(R.id.ivCreateAlbumGirl)
        tvBirthday = findViewById(R.id.tvCreateAlbumBirthday)
        flBirthday = findViewById(R.id.flCreateAlbumBirthday)
        tvRelation = findViewById(R.id.tvCreateAlbumRelation)
        flRelation = findViewById(R.id.flCreateAlbumRelation)
        flCamera = findViewById(R.id.flCreateAlbumCamera)
        createAlbumPresenter = CreateAlbumPresenter()
        createAlbumPresenter.setView(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAvatar(ID_ACCOUNT: Int?) {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result?.resultCode == 123) {
                val intent = result.data
                if (intent != null) {
                    val bitmap: Bitmap = intent.extras?.get("uriImage") as Bitmap
                    bitmapAvatar = true
                    //convert bitmap to uri
                    val uri = convertUri(bitmap)
                    val path_image = getRealPathFromUri(uri)
                    ivAvatar.setImageBitmap(bitmap)
                    setBackgroundButton()
                    btnCreate.setOnClickListener {
                        sendSingleImage(path_image)
                        createAlbumPresenter.createAlbum(
                            singleFile,
                            rqIdaccount, rqName, rqGender, rqBirthday, rqRelation, this
                        )
                    }
                    flCamera.visibility = View.GONE
                }
            }

            if (result?.resultCode == 234) {
                val intent = result.data
                if (intent != null) {
                    val dataImage: String = intent.extras?.get("uri") as String
                    ivAvatar.setImageBitmap(BitmapFactory.decodeFile(dataImage))
                    bitmapAvatar = true
                    setBackgroundButton()
                    btnCreate.setOnClickListener {
                        sendSingleImage(dataImage)
                        createAlbumPresenter.createAlbum(
                            singleFile,
                            rqIdaccount, rqName, rqGender, rqBirthday, rqRelation, this
                        )
                    }
                    flCamera.visibility = View.GONE
                }
            }
        }
    }

    private fun changeButton() {
        edtName.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeybroad(v)
            }
        }

        edtName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (count > 0) {
                    setBackgroundButton()
                } else {
                    btnCreate.setBackgroundResource(R.drawable.shape_gray_bg_corner_20)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > 0) {
                    setBackgroundButton()
                } else {
                    btnCreate.setBackgroundResource(R.drawable.shape_gray_bg_corner_20)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                setBackgroundButton()

            }
        })
    }

    fun getUriBaby() {
        ivAvatar.setOnClickListener {
            val intent = Intent(this, PhotoFolderActivity::class.java)
            activityResultLauncher.launch(intent)
        }
        ivBackHome.setOnClickListener {
            openBackDialog()
        }
    }


    private fun hideKeybroad(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun getName(name: String) {
        tvRelation.text = name
        setBackgroundButton()
    }

    fun setBackgroundButton() {
        if (bitmapAvatar && edtName.text.toString() != "" && tvBirthday.text != "" && tvRelation.text != "")
            btnCreate.setBackgroundResource(R.drawable.shape_orange_bg_corner_20)
    }

    fun getGenderAlbum(): Int {
        ivBoy.setOnClickListener {
            ivBoy.setBackgroundResource(R.drawable.shape_cir_yellow_bg_corner_large)
            ivGirl.setBackgroundResource(R.drawable.shape_cir_grey_bg_corner_90)
            select = 1
        }
        ivGirl.setOnClickListener {
            ivGirl.setBackgroundResource(R.drawable.shape_cir_yellow_bg_corner_large)
            ivBoy.setBackgroundResource(R.drawable.shape_cir_grey_bg_corner_90)
            select = 0
        }
        return select
    }


    fun getBirthdayAlbum() {

        flBirthday.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

            val datePickerDialog =
                DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(
                        view: DatePicker?,
                        year: Int,
                        month: Int,
                        dayOfMonth: Int
                    ) {
                        tvBirthday.text = "${dayOfMonth}/${month + 1}/${year}"
                    }
                }, year, month, dayOfMonth)
            datePickerDialog.setOnCancelListener {
                if (tvBirthday.text != "") {
                    setBackgroundButton()
                }
            }
            datePickerDialog.setOnDismissListener {
                if (tvBirthday.text != "") {
                    setBackgroundButton()
                }
            }
            datePickerDialog.show()
        }
    }

    fun getRelationAlbum() {
        flRelation.setOnClickListener {
            val dialogRelationFragment = DialogRelationFragment()
            dialogRelationFragment.show(this.supportFragmentManager, dialogRelationFragment.tag)
        }
    }

    override fun onSuccess(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        val intent = Intent(applicationContext, HomeActivity::class.java)
        intent.putExtra("idalbum", "1")
        startActivity(intent)
    }

    override fun onFail(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onError(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }


    fun sendSingleImage(pathImage: String) {
        file = File(pathImage)
        file_path = file.absolutePath + System.currentTimeMillis() + ".png"
        requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        singleFile = MultipartBody.Part.createFormData("file", file_path, requestBody)


        val name = edtName.text.toString()
        val gender = getGenderAlbum()
        strGender = if (gender == 1) {
            "1"
        } else {
            "0"
        }
        val birthday: String = tvBirthday.text.toString()
        val relation: String = tvRelation.text.toString()

        rqIdaccount = RequestBody.create(MediaType.parse("multipart/form-data"), "1")
        rqName = RequestBody.create(MediaType.parse("multipart/form-data"), name)
        rqGender = RequestBody.create(MediaType.parse("multipart/form-data"), strGender)
        rqBirthday = RequestBody.create(MediaType.parse("multipart/form-data"), birthday)
        rqRelation = RequestBody.create(MediaType.parse("multipart/form-data"), relation)
    }


    fun openBackDialog() {
        val dialog = Dialog(this)
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
            finish()
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

        val cursor = this.contentResolver.query(
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
            this.getContentResolver(),
            bitmap,
            "Title",
            null
        )
        return Uri.parse(path)
    }
}