package ai.ftech.babyphoto.screen.changepass

import ai.ftech.babyphoto.base.Constant
import ai.ftech.babyphoto.base.Utils
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.model.ResponseModel
import ai.ftech.babyphoto.screen.detailaccount.DetailAccount
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_change_pass.*
import kotlinx.android.synthetic.main.activity_detail_account.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePassPresenter(private var view: IChangePassContract.View) {
    fun validPassword(oldPass: String, newPass: String, reNewPass: String){
        view.onCheckNull(ChangePassState.PASS_NULL, "pass is null")
        if (oldPass != Constant.account.password){
            return view.onCheckPass(ChangePassState.PASS_NOT_FOUND, "pass not found")
        }
        if (!Utils().isValidPassCharacter(newPass) || !Utils().isValidPassCount(newPass)){
            return view.onCheckNewPass(ChangePassState.PASS_NOT_VALID, "new pass is not valid")
        }
        if (!Utils().isValidPassCharacter(newPass) || !Utils().isValidPassCount(newPass) || !Utils().isMatchPass
                (newPass, reNewPass)){
            return view.onCheckReNewPass(ChangePassState.PASS_NOT_MATCH, "re new pass is not match")
        }

        view.onCheckPass(ChangePassState.SUCCESS, "pass found")

        if (oldPass.isNotEmpty() && newPass.isNotEmpty() && reNewPass.isNotEmpty()){
            return view.onCheckNull(ChangePassState.PASS_NOT_NULL, "pass is null")
        }
    }

    fun submit(dialog: Dialog, idaccount: Int, pass: String) {
        APIService.base().updatePass(
            idaccount, pass
        ).enqueue(
            object : Callback<ResponseModel<Any>> {
                override fun onResponse(
                    call: Call<ResponseModel<Any>>,
                    response: Response<ResponseModel<Any>>
                ) {
                    Constant.account.password = pass
                    view.onSubmit(
                        ChangePassState.UPDATE_PASS_SUCCESS,
                        "Update password success",
                        dialog
                    )

                    Log.d("TAG", "onResponse: update pass success")
                }

                override fun onFailure(call: Call<ResponseModel<Any>>, t: Throwable) {
                    view.onSubmit(
                        ChangePassState.UPDATE_PASS_FAILED,
                        "Update password failed",
                        dialog
                    )
                    Log.d("TAG", "onFailure: update pass fail")
                }
            }
        )
    }

}
