package ai.ftech.babyphoto.screen.album

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.model.ICreateAlbum
import ai.ftech.babyphoto.screen.fragment.DialogRelationFragment
import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.TextView
import java.util.*

class CreateAlbumPresenter(activity: CreateAlbumActivity) : ICreateAlbum {
    private val view = activity
    var select = 1
    override fun getGenderAlbum() : Int{
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

    override fun getRelationAlbum(relation1: String) {
        view.flRelation.setOnClickListener {
            val dialogRelationFragment = DialogRelationFragment()
            dialogRelationFragment.show(view.supportFragmentManager, dialogRelationFragment.tag)
        }
        view.tvRelation.text = relation1
    }


}

