package ai.ftech.babyphoto.screen.activity

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.screen.presenter.CreateAlbumPresenter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class CreateAlbumActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var ivBackHome: ImageView
    lateinit var btnCreate: Button
    lateinit var ivAvatar: ImageView
    lateinit var edtName: EditText
    lateinit var ivBoy: ImageView
    lateinit var ivGirl: ImageView
    lateinit var tvBirthday: TextView
    lateinit var flBirthday: FrameLayout
    lateinit var tvRelation: TextView
    lateinit var flRelation: FrameLayout
    lateinit var createAlbumPresenter: CreateAlbumPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_album_activity)
        initView()

        ivAvatar.setOnClickListener {
            val intent = Intent(this, PhotoFolderActivity::class.java)
            startActivity(intent)
        }
        flRelation.setOnClickListener(this)
        flBirthday.setOnClickListener(this)
        createAlbumPresenter.getGenderAlbum()
        edtName.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeybroad(v)
            }
        }
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
        createAlbumPresenter = CreateAlbumPresenter(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.flCreateAlbumRelation ->
                createAlbumPresenter.getRelationAlbum()
            else -> createAlbumPresenter.getBirthdayAlbum(tvBirthday)
        }
    }

    private fun hideKeybroad(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}