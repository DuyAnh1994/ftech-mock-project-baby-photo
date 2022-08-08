package ai.ftech.babyphoto.screen.album

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.model.ICreateAlbum
import ai.ftech.babyphoto.screen.fragment.DialogRelationFragment
import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class CreateAlbumPresenter(activity: CreateAlbumActivity) : ICreateAlbum {
    private val view = activity
    lateinit var birthday: Date
    override fun getNameAlbum(): String {
        return view.edtName.text.toString()
    }

    override fun getGenderAlbum(): Boolean {
        var select = true
        view.ivBoy.setOnClickListener {
            view.ivBoy.setBackgroundResource(R.drawable.shape_cir_yellow_bg_corner_large)
            view.ivGirl.setBackgroundResource(R.drawable.shape_cir_grey_bg_corner_90)
            select = true
        }
        view.ivGirl.setOnClickListener {
            view.ivGirl.setBackgroundResource(R.drawable.shape_cir_yellow_bg_corner_large)
            view.ivBoy.setBackgroundResource(R.drawable.shape_cir_grey_bg_corner_90)
            select = false
        }

        return select
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun getBirthdayAlbum(tvBirthday: TextView): String {

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
        val strBirthday = tvBirthday.text.toString()
        Log.d("AAA", "getBirthdayAlbum: ${birthday}")
        return strBirthday
    }

    override fun getRelationAlbum(relation: String): String {
        view.flRelation.setOnClickListener {
            val dialogRelationFragment = DialogRelationFragment()
            dialogRelationFragment.show(view.supportFragmentManager, dialogRelationFragment.tag)
        }
        view.tvRelation.text = relation
        return relation
    }

}

