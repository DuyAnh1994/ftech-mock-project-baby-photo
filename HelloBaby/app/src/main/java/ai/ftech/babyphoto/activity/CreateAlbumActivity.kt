package ai.ftech.babyphoto.activity

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.fragment.DialogRelationFragment
import ai.ftech.babyphoto.model.ICreateAlbum
import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class CreateAlbumActivity : AppCompatActivity(), ICreateAlbum {
    private var ivBackHome: ImageView? = null
    private var btnCreate: Button? = null
    private var ivAvatar: ImageView? = null
    private var edtName: EditText? = null
    private var ivBoy: ImageView? = null
    private var ivGirl: ImageView? = null
    private var tvBirthday: TextView? = null
    private var flBirthday: FrameLayout? = null
    private var tvRelation: TextView? = null
    private var flRelation: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_album_activity)
        initView()
        getGenderAlbum()
        getBirthdayAlbum()
        getRelationAlbum()
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
    }

    override fun getNameAlbum(): String {
        edtName?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeybroad(v)
            }
        }
        return edtName?.text.toString()
    }

    override fun getGenderAlbum(): Int {
        var select: Int = 1
        ivBoy?.setOnClickListener {
            ivBoy?.setBackgroundResource(R.drawable.shape_cir_yellow_bg_corner_large)
            ivGirl?.setBackgroundResource(R.drawable.shape_cir_grey_bg_corner_90)
            select = 1
        }
        ivGirl?.setOnClickListener {
            ivGirl?.setBackgroundResource(R.drawable.shape_cir_yellow_bg_corner_large)
            ivBoy?.setBackgroundResource(R.drawable.shape_cir_grey_bg_corner_90)
            select = 0
        }

        return select
    }

    override fun getBirthdayAlbum(): String {
        flBirthday?.setOnClickListener {
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
                        tvBirthday?.text = "${dayOfMonth}/${month + 1}/${year}"
                    }

                }, year, month, dayOfMonth)
            datePickerDialog.show()
        }
        return tvBirthday?.text.toString().trim()
    }

    override fun getRelationAlbum(): String {
        flRelation?.setOnClickListener {
            val dialogRelationFragment = DialogRelationFragment()
            dialogRelationFragment.show(this.supportFragmentManager, dialogRelationFragment.tag)
        }
        return ""
    }

    override fun hideKeybroad(view: View) {
        val inputMethodManager =  getSystemService(Activity.INPUT_METHOD_SERVICE)  as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }


}