package ai.ftech.babyphoto.screen.createalbum

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.screen.createalbum.preview.PhotoFolderActivity
import ai.ftech.babyphoto.screen.createalbum.relation.DialogRelationFragment
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import de.hdodenhof.circleimageview.CircleImageView


class CreateAlbumActivity : AppCompatActivity(), DialogRelationFragment.ICreateName {
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
     var bitmapAvatar: Boolean =false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_album_activity)
        initView()
        getUriBaby()
        createAlbumPresenter.getGenderAlbum()
        createAlbumPresenter.getRelationAlbum(tvRelation.text.toString())
        createAlbumPresenter.getBirthdayAlbum(tvBirthday)
        changeButton()
        getAvatar()
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
        createAlbumPresenter = CreateAlbumPresenter(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAvatar() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result?.resultCode == 123) {
                val intent = result.data
                if (intent != null) {
                    val bitmap: Bitmap = intent.extras?.get("uriImage") as Bitmap
                    bitmapAvatar = true
                    //convert bitmap to uri
                    val uri =createAlbumPresenter.convertUri(bitmap)
                    ivAvatar.setImageBitmap(bitmap)
                    setBackgroundButton()
                    createAlbumPresenter.sendImageToFirebase(uri,"")
                    flCamera.visibility = View.GONE
                }
            }

            if (result?.resultCode == 234) {
                val intent = result.data
                if (intent != null) {
                    val dataImage: String = intent.extras?.get("uri")  as String
                    ivAvatar.setImageBitmap(BitmapFactory.decodeFile(dataImage))
                    bitmapAvatar = true
                    val uri : Uri =  Uri.parse("")
                    setBackgroundButton()
                    createAlbumPresenter.sendImageToFirebase(uri,dataImage)
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

    private fun getUriBaby() {
        ivAvatar.setOnClickListener {
            val intent = Intent(this, PhotoFolderActivity::class.java)
            activityResultLauncher.launch(intent)
        }
        ivBackHome.setOnClickListener {
            createAlbumPresenter.openBackDialog()
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
}