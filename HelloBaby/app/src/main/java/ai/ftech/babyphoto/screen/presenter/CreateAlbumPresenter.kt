package ai.ftech.babyphoto.screen.presenter

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.model.ICreateAlbum
import ai.ftech.babyphoto.screen.activity.CreateAlbumActivity
import ai.ftech.babyphoto.screen.fragment.DialogRelationFragment
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import java.util.*

class CreateAlbumPresenter(activity: CreateAlbumActivity) : ICreateAlbum {
    private val view = activity
    override fun getNameAlbum(): String {
        return view.edtName.text.toString()
    }

    override fun getGenderAlbum(): Int {
        var select: Int = 1
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


    override fun getBirthdayAlbum(tvBirthday : TextView): String {

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
        return view.tvBirthday.text.toString().trim()
    }

    override fun getRelationAlbum(): String {
        view.flRelation.setOnClickListener {
            val dialogRelationFragment = DialogRelationFragment()
            val bundle : Bundle = Bundle()
            dialogRelationFragment.show(view.supportFragmentManager, dialogRelationFragment.tag)
        }
        return ""
    }


}