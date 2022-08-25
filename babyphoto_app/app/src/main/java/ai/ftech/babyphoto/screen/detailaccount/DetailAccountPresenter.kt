package ai.ftech.babyphoto.screen.detailaccount

import ai.ftech.babyphoto.MainActivity
import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.Constant
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.model.AccountUpdate
import ai.ftech.babyphoto.model.ResponseModel
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail_account.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailAccountPresenter(private var view: IDetailAccountContract.View) {

    fun updateAccount(account: AccountUpdate) {
        APIService.base().updateAccount(
            account.email, account.firstname, account.lastname,
            account.idaccount
        ).enqueue(
            object : Callback<ResponseModel<Any>> {
                override fun onResponse(call: Call<ResponseModel<Any>>, response: Response<ResponseModel<Any>>) {
                    Constant.account.email = account.email
                    Constant.account.firstname = account.firstname
                    Constant.account.lastname = account.lastname
                    view.onUpdateAccount(DetailAccountState.SUCCESS, "Change Account Success!")
                    Log.d("TAG", "onResponse: Update Success")

                }
                override fun onFailure(call: Call<ResponseModel<Any>>, t: Throwable) {
                    view.onUpdateAccount(DetailAccountState.SUCCESS, "Change Account Failed!")
                    Log.d("TAG", "ERROR: Update Fail")
                }
            })
    }
}