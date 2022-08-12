package ai.ftech.babyphoto.screen.createalbum

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Data
import ai.ftech.babyphoto.screen.createalbum.relation.DialogRelationFragment
import ai.ftech.babyphoto.screen.home.HomeActivity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.Gravity
import android.view.Window
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.random.Random

class CreateAlbumPresenter(activity: CreateAlbumActivity) : ICreateAlbum {
    private val view = activity
    var select = 1
    override fun getGenderAlbum(): Int {
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

    override fun getRelationAlbum(relation: String) {
        view.flRelation.setOnClickListener {
            val dialogRelationFragment = DialogRelationFragment()
            dialogRelationFragment.show(view.supportFragmentManager, dialogRelationFragment.tag)
        }
        view.tvRelation.text = relation
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createAlbum(base64Avatar: String) {
        view.btnCreate.setOnClickListener {
            var progressdialog = ProgressDialog(view, R.style.AppCompatAlertDialogStyle)
            progressdialog.setMessage("Updating")
            progressdialog.setCancelable(false)
            progressdialog.show()
            val ID_ALBUM: Int = Random.nextInt(1000, 999999999)
            var name = view.edtName.text.toString()
            var gender = getGenderAlbum()
            var birthday: String = view.tvBirthday.text.toString()
            var relation: String = view.tvRelation.text.toString()
            if (base64Avatar != "" && name != "" && relation != "" && birthday != "") {
                val dataService = APIService.base()
                val callback: Call<Data<String>> = dataService.albumInsert(
                    ID_ALBUM,
                    129,
                    base64Avatar,
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
                            val intent = Intent(view, HomeActivity::class.java)
                            view.startActivity(intent)
                            Toast.makeText(
                                view,
                                "create album successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            progressdialog.dismiss()
                        } else {
                            Toast.makeText(
                                view,
                                response.body()!!.msg + ", please retry",
                                Toast.LENGTH_SHORT
                            ).show()
                            progressdialog.dismiss()
                        }
                    }

                    override fun onFailure(call: Call<Data<String>>, t: Throwable) {
                        Toast.makeText(
                            view,
                            t.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        progressdialog.dismiss()
                    }
                })
            }
        }
    }

    fun openBackDialog() {
        val dialog = Dialog(view)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_back_create_album_layout)
        val window: Window = dialog.window ?: return
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        val windowAttributes = window.attributes
        windowAttributes.gravity = Gravity.CENTER
        window.attributes = windowAttributes
        val btnCancel: Button = dialog.findViewById(R.id.btnDialogBacKCancel)
        val btnOK: Button = dialog.findViewById(R.id.btnDialogBacKOk)
        btnOK.setOnClickListener {
            view.finish()
            dialog.dismiss()
        }
        btnCancel.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

}

