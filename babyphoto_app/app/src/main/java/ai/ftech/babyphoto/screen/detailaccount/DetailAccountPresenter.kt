package ai.ftech.babyphoto.screen.detailaccount

import ai.ftech.babyphoto.data.Constant
import ai.ftech.babyphoto.data.model.AccountUpdate
import ai.ftech.babyphoto.data.model.ResponseModel
import ai.ftech.babyphoto.data.service.APIService
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailAccountPresenter(private var view: IDetailAccountContract.View) :IDetailAccountContract.IPresenter{

    override fun updateAccount(account: AccountUpdate) {
        APIService.base().updateAccount(
            account.email, account.firstname, account.lastname,
            account.idaccount
        ).enqueue(
            object : Callback<ResponseModel<Any>> {
                override fun onResponse(
                    call: Call<ResponseModel<Any>>,
                    response: Response<ResponseModel<Any>>
                ) {
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