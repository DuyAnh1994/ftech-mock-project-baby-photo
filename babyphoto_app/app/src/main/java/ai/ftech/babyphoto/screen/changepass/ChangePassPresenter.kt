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

    fun checkPass(pass: String) {
        var hasPass = pass == Constant.account.password
        if (!hasPass) {
            view.onCheckPass(ChangePassState.PASS_NOT_FOUND, "pass not found")
        } else {
            view.onCheckPass(ChangePassState.SUCCESS, "pass found")
        }
    }

    fun checkNewPass(pass: String) {
        val isValidPassCharacter = Utils().isValidPassCharacter(pass)
        val isValidPassCount = Utils().isValidPassCount(pass)
        if (!isValidPassCharacter || !isValidPassCount) {
            view.onCheckNewPass(ChangePassState.PASS_NOT_VALID, "new pass is not valid")
        } else {
            view.onCheckNewPass(ChangePassState.PASS_VALID, "new pass is valid")
        }
    }

    fun checkReNewPass(pass: String, rePass: String) {
        val isMatchPass = Utils().isMatchPass(pass, rePass)
        if (!isMatchPass) {
            view.onCheckReNewPass(ChangePassState.PASS_NOT_MATCH, "re new pass is not match")
        } else {
            view.onCheckReNewPass(ChangePassState.PASS_MATCH, "new pass is match")
        }
    }

    fun checkNull(
        curPass: String,
        newPass: String,
        reNewPass: String,
        statePass: ChangePassState?,
        stateNewPass: ChangePassState?,
        stateReNewPass: ChangePassState?
    ) {
        val isNotNull = curPass != "" && newPass != "" && reNewPass != ""
        if (isNotNull && statePass == ChangePassState.SUCCESS && stateNewPass == ChangePassState.PASS_VALID && stateReNewPass == ChangePassState.PASS_MATCH
        ) {
            view.onCheckNull(ChangePassState.PASS_NOT_NULL, "pass is not null")
        } else {
            view.onCheckNull(ChangePassState.PASS_NULL, "pass is null")
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
