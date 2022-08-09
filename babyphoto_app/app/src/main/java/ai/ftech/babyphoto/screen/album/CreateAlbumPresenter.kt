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

    override fun getGenderAlbum() : Int{
        var select = 1
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
            setBackgroundButton()
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
            setBackgroundButton()
            val dialogRelationFragment = DialogRelationFragment()
            dialogRelationFragment.show(view.supportFragmentManager, dialogRelationFragment.tag)
        }
        view.tvRelation.text = relation
    }
    fun setBackgroundButton() {
        if (view.edtName.text.toString() != "" && view.tvBirthday.text != "" && view.tvRelation.text != "")
            view.btnCreate.setBackgroundResource(R.drawable.shape_orange_bg_corner_20)
    }


}

