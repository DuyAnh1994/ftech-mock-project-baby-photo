package ai.ftech.babyphoto.screen.album

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.base.service.DataService
import ai.ftech.babyphoto.model.Album
import ai.ftech.babyphoto.model.Data
import ai.ftech.babyphoto.screen.fragment.DialogRelationFragment
import ai.ftech.babyphoto.screen.home.HomeActivity
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*


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
    private var base64Avatar: String? = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_album_activity)
        initView()
        getUriBaby()
        getAvatar()
        createAlbumPresenter.getNameAlbum()
        createAlbumPresenter.getGenderAlbum()
        createAlbumPresenter.getBirthdayAlbum(tvBirthday)
        createAlbumPresenter.getRelationAlbum(tvRelation.text.toString())
        createAlbum()

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

                    //chuyển bitmap về base64
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                    val b: ByteArray = baos.toByteArray()
                    base64Avatar = Base64.getEncoder().encodeToString(b)

                    //chuyển base64 về bitmap
                    val encodeByte: ByteArray = Base64.getDecoder().decode(base64Avatar)
                    val bitmapBaby =
                        BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.count())
                    ivAvatar.setImageBitmap(bitmapBaby)

                    flCamera.visibility = View.GONE
                }
            }

            if (result?.resultCode == 234) {
                val intent = result.data
                if (intent != null) {
                    val bitmap: Bitmap = intent.extras?.get("bitmap") as Bitmap

                    //chuyển bitmap về base64
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                    val b: ByteArray = baos.toByteArray()
                    base64Avatar = Base64.getEncoder().encodeToString(b)

                    //chuyển base64 về bitmap
                    val encodeByte: ByteArray = Base64.getDecoder().decode(base64Avatar)
                    val bitmapBaby =
                        BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.count())
                    ivAvatar.setImageBitmap(bitmapBaby)

                    flCamera.visibility = View.GONE
                }
            }
        }
        edtName.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeybroad(v)
            }
        }
    }

    private fun getUriBaby() {
        ivAvatar.setOnClickListener {
            val intent = Intent(this, PhotoFolderActivity::class.java)
            activityResultLauncher.launch(intent)
        }
    }

    override fun getName(name: String) {
        tvRelation.text = name
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createAlbum() {
        btnCreate.setOnClickListener {
            val name: String = createAlbumPresenter.getNameAlbum()
            val gender: Int = createAlbumPresenter.getGenderAlbum()
            val relation: String = createAlbumPresenter.getRelationAlbum(tvRelation.text.toString())
            val birthday: String = createAlbumPresenter.getBirthdayAlbum(tvBirthday)

            if (base64Avatar != "" && name != "" && relation != "" && birthday != "") {
                btnCreate.setBackgroundResource(R.drawable.shape_orange_bg_corner_20)
                val dataService = APIService.base()
                val callback = dataService.albumInsert(
                    456,
                    234,
                    base64Avatar!!,
                    name,
                    gender,
                    birthday,
                    relation,
                    0
                )
                callback.enqueue(object : Callback<Data<Album>> {
                    override fun onResponse(
                        call: Call<Data<Album>>,
                        response: Response<Data<Album>>
                    ) {
                        if (response.body() != null) {
                            Toast.makeText(
                                CreateAlbumActivity(),
                                response.body()!!.msg,
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                        val intent = Intent(CreateAlbumActivity(), HomeActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onFailure(call: Call<Data<Album>>, t: Throwable) {
                        Log.d("AAA", "onFailure: ${t.message}")
                    }

                })
            }
        }
    }

    private fun hideKeybroad(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}