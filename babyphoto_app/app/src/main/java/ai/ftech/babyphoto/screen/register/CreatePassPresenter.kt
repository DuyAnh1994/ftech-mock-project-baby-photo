package ai.ftech.babyphoto.screen.register

import ai.ftech.babyphoto.MainActivity
import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.Utils
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.screen.login.AccountLogin
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

class CreatePassPresenter(private var view: ICreatePassContract.View) {
    //    private val view = activity
    private var account: Account? = null


    fun checkPass(pass: String, rePass: String) {
        val isValidPassCharacter = Utils().isValidPassCharacter(pass) && Utils().isValidPassCharacter(rePass)
        val isValidPassCount = Utils().isValidPassCount(pass) && Utils().isValidPassCount(rePass)
        val isMatchPass = Utils().isMatchPass(pass, rePass)

        if (!isValidPassCharacter)
            return view.onCheckPass(RegisterState.PASS_NOT_VALID, "pass is not valid")

        if (!isValidPassCount)
            return view.onCheckPass(RegisterState.PASS_NOT_VALID, "pass is not valid")

        if (!isMatchPass) return view.onCheckPass(RegisterState.PASS_NOT_MATCH, "repass is not match")

        view.onCheckPass(RegisterState.SUCCESS, "pass is valid")
    }

    fun submit(state: RegisterState, pass: String, rePass: String, accountP: Account?) {
        account = accountP
        when (state) {
            RegisterState.SUCCESS -> {
                account?.password = rePass
                APIService.base().insertAccount(
                    account!!.email,
                    account!!.password,
                    account!!.firstname,
                    account!!.lastname
                ).enqueue(
                    object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            view.onInsertAccount(RegisterState.PASS_NOT_MATCH, "pass is not match", "")
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            view.onInsertAccount(RegisterState.SUCCESS, "pass is match", account!!.email)

                        }
                    }
                )
            }
            else -> {}
        }
    }
}