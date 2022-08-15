package ai.ftech.babyphoto.screen.register

import ai.ftech.babyphoto.MainActivity
import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.Utils
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.model.Data
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_create_pass.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePassPresenter(activity: ActivityCreatePass) {
    private val view = activity
    private var account: Account? = null
    private val bundle = view.intent.extras?.let {
        it.apply {
            account = Gson().fromJson(get("account") as String, Account::class.java)
        }
    }

    fun checkPass(pass: String): Boolean {
        val isValidPassCharacter = Utils().isValidPassCharacter(pass)
        val isValidPassCount = Utils().isValidPassCount(pass)
        if (!isValidPassCharacter || !isValidPassCount
        )
            view.tvRegisterWarningPass.setTextColor(Color.parseColor("#DC143C"))
        else
            view.tvRegisterWarningPass.setTextColor(Color.parseColor("#FFFFFF"))
        return !isValidPassCount || !isValidPassCharacter
    }

    fun checkRePass(pass: String, rePass: String): Boolean {
        val isMatchPass = Utils().isMatchPass(pass, rePass)
        if (!isMatchPass) {
            view.tvRegisterWarningPass.text = "Confirm password doesn't match"
            view.tvRegisterWarningPass.setTextColor(Color.parseColor("#DC143C"))
            view.btnRegisterNext3.setBackgroundResource(R.drawable.selector_rec_gray_color_orange_selected)
        } else {
            view.tvRegisterWarningPass.setTextColor(Color.parseColor("#FFFFFF"))
            view.btnRegisterNext3.setBackgroundResource(R.drawable.selector_rec_orange_color)
        }
        return !isMatchPass
    }

    fun openDialog(): Dialog {
        var dialogLoadPass = Dialog(view)
        dialogLoadPass.setContentView(R.layout.dialog_loading_register_layout)
        dialogLoadPass.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        view.btnRegisterNext3.setOnClickListener {
            dialogLoadPass.dismiss()
        }
        dialogLoadPass.show()
        return dialogLoadPass
    }

    fun submit() {
        Log.d("TAG", "submit: ${Gson().toJson(account)} ")
        if (checkRePass(
                view.tieRegisterPass.text.toString(),
                view.tieRegisterRePass.text.toString()
            )
        ) return

        account?.password = view.tieRegisterPass.text.toString()

        print(Gson().toJson(account))

        val dialog = openDialog()

        APIService.base().insertAccount(
            account!!.email,
            account!!.password,
            account!!.firstname,
            account!!.lastname,
        ).enqueue(
            object : Callback<Data<String>> {
                override fun onResponse(
                    call: Call<Data<String>>,
                    response: Response<Data<String>>
                ) {
                    dialog.dismiss()
                    Toast.makeText(
                        view.applicationContext,
                        response.body()!!.msg,
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(view, MainActivity::class.java)
                    view.startActivity(intent)
                }

                override fun onFailure(call: Call<Data<String>>, t: Throwable) {
                    dialog.dismiss()
                    Toast.makeText(view.applicationContext, "Insert failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        )
    }
}