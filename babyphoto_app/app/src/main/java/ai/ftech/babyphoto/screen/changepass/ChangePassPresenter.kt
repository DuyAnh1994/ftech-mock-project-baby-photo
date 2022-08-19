package ai.ftech.babyphoto.screen.changepass

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.Utils
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.model.ResponseModel
import ai.ftech.babyphoto.screen.detailaccount.DetailAccount
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_change_pass.*
import kotlinx.android.synthetic.main.activity_detail_account.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePassPresenter(activity: ChangePass) {
    private val view = activity
    private var lAccount: MutableList<Account> = mutableListOf()
    private var passTest: String = ""
    private val bundle: Bundle? = view.intent.extras
    private val idaccount = bundle?.get("idaccount")
    fun getAccountId() {
        APIService().base().getAccountId(idaccount as Int).enqueue(
            object : Callback<ResponseModel<List<Account>>> {
                override fun onResponse(
                    call: Call<ResponseModel<List<Account>>>,
                    response: Response<ResponseModel<List<Account>>>
                ) {
                    val res = response.body() as ResponseModel<List<Account>>
                    lAccount.addAll(res.data)
                    passTest = lAccount[0].password
                }

                override fun onFailure(call: Call<ResponseModel<List<Account>>>, t: Throwable) {
                    Toast.makeText(view, "Get Account failed", Toast.LENGTH_SHORT).show()
                }

            }
        )
    }

    fun checkPass(pass: String): Boolean {
        var hasPass = pass == passTest
        if (!hasPass) {
            view.tvChangePassWarning.text = "You password was incorrect."
            view.tvChangePassWarning.setTextColor(Color.parseColor("#FF4B4B"))
        } else {
            view.tvChangePassWarning.text = ""
        }
        return !hasPass
    }

    fun checkNewPass(pass: String): Boolean {
        val isValidPassCharacter = Utils().isValidPassCharacter(pass)
        val isValidPassCount = Utils().isValidPassCount(pass)
        if (!isValidPassCharacter || !isValidPassCount) {
            view.tvChangePassWarning.text = "Passwords are between 6 and 8 characters, do not use special characters"
            view.tvChangePassWarning.setTextColor(Color.parseColor("#FF4B4B"))
        } else
        {
            view.tvChangePassWarning.text = ""
        }
        return !isValidPassCount || !isValidPassCharacter
    }

    fun checkReNewPass(pass: String, rePass: String): Boolean {
        val isMatchPass = Utils().isMatchPass(pass,rePass)
        if (!isMatchPass) {
            view.tvChangePassWarning.text = "Confirm password doesn't match"
            view.tvChangePassWarning.setTextColor(Color.parseColor("#FF4B4B"))
        } else {
            view.tvChangePassWarning.text = ""
        }
        return !isMatchPass
    }
    fun checkNull(curPass: String, newPass: String, reNewPass: String): Boolean{
        val isNotNull = curPass!=""&&newPass!=""&&reNewPass!=""
        if (isNotNull && !checkPass(curPass) && !checkNewPass(newPass) && !checkReNewPass(reNewPass, newPass)){
            view.tvSaveChange.text = "Save"
            view.tvSaveChange.visibility = View.VISIBLE
        }else{
            view.tvSaveChange.text = ""
            view.tvSaveChange.visibility = View.INVISIBLE
        }
        return isNotNull && !checkPass(curPass) && !checkNewPass(newPass) && !checkReNewPass(reNewPass, newPass)
    }
    fun openDialog(): Dialog {
        var dialogLoadPass = Dialog(view)
        dialogLoadPass.setContentView(R.layout.dialog_loading_register_layout)
        dialogLoadPass.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogLoadPass.show()
        return dialogLoadPass
    }
    fun backScreen(){
        view.finish()
        showSnackbar()
    }
    fun showSnackbar() {
        val mSnackBar = Snackbar.make(view.detailAcccountMain, "Your new password has updated", Snackbar.LENGTH_LONG)
//        mSnackBar.setAction("close", View.OnClickListener {
//
//        })
            .setActionTextColor(Color.parseColor("#FFFFFF"))
            .setBackgroundTint(Color.parseColor("#FECE00"))
            .setTextColor(Color.parseColor("#FFFFFF"))

        val params = mSnackBar.view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        mSnackBar.view.layoutParams = params
        mSnackBar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        mSnackBar.show()
    }
    fun submit() {

        val dialog = openDialog()

        APIService().base().updatePass(
            idaccount as Int, view.tieNewPass.text.toString()
        ).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    dialog.dismiss()

                    Log.d("TAG", "onResponse: update pass success")
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    dialog.dismiss()
                    val intent = Intent(view, DetailAccount::class.java)
                    intent.putExtra("showTop", "showTop")
                    view.startActivity(intent)
                    Log.d("TAG", "onFailure: update pass fail")
                }
            }
        )
    }

}
