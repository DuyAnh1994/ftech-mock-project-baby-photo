package ai.ftech.babyphoto.screen.album

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Data
import ai.ftech.babyphoto.screen.fragment.DialogRelationFragment
import ai.ftech.babyphoto.screen.home.HomeActivity
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import kotlin.random.Random


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
    var base64Avatar: String? = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_album_activity)
        initView()
        getUriBaby()
        getAvatar()
        createAlbumPresenter.getGenderAlbum()
        createAlbumPresenter.getRelationAlbum(tvRelation.text.toString())
        createAlbumPresenter.getBirthdayAlbum(tvBirthday)
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
                    setBackgroundButton()

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
                    setBackgroundButton()

                    flCamera.visibility = View.GONE
                }
            }
        }
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
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createAlbum() {
        btnCreate.setOnClickListener {
            val ID_ALBUM: Int = Random.nextInt(1000, 999999999)
            var name = edtName.text.toString()
            var gender = createAlbumPresenter.getGenderAlbum()
            var birthday: String = tvBirthday.text.toString()
            var relation: String = tvRelation.text.toString()
            if (base64Avatar != "" && name != "" && relation != "" && birthday!= "") {
                val dataService = APIService.base()
                val callback: Call<Data<String>> = dataService.albumInsert(
                    ID_ALBUM,
                    1299999293,
                    base64Avatar!!,
                    name,
                    gender,
                    birthday,
                    relation,
                    0
                )
                callback.enqueue(object : Callback<Data<String>> {
                    override fun onResponse(
                        call: Call<Data<String>>,
                        response: Response<Data<String>>
                    ) {
                        if (response.body()!!.code == "code32") {
                            val intent = Intent(applicationContext, HomeActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(
                                applicationContext,
                                "create album successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                response.body()!!.msg + ", Please retry",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Data<String>>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            t.message,
                            Toast.LENGTH_SHORT
                        ).show()
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

    override fun getName(name: String) {
        tvRelation.text = name
        setBackgroundButton()
    }

    fun setBackgroundButton() {
        if (base64Avatar != "" && edtName.text.toString() != "" && tvBirthday.text != "" && tvRelation.text != "")
            btnCreate.setBackgroundResource(R.drawable.shape_orange_bg_corner_20)
    }
}