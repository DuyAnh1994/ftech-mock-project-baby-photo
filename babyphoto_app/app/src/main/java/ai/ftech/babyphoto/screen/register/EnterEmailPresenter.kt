package ai.ftech.babyphoto.screen.register

import ai.ftech.babyphoto.data.Utils
import ai.ftech.babyphoto.data.service.APIService
import ai.ftech.babyphoto.data.model.Account
import ai.ftech.babyphoto.data.model.ResponseModel
import android.app.Dialog
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnterEmailPresenter(private var view: IEnterEmailContract.View) {
    //khai báo service
    private val apiService = APIService.base()

    fun checkEmail(dialog: Dialog, email: String, account: Account?) {
        apiService.checkEmail(email).enqueue(
            object : Callback<ResponseModel<List<String>>> {
                override fun onResponse(
                    call: Call<ResponseModel<List<String>>>,
                    response: Response<ResponseModel<List<String>>>
                ) {
                    dialog.dismiss()
                    if (response.body() != null && "code12" == response.body()?.code) {
                        return view.onCheckMail(RegisterState.EMAIL_EXIST, "email is exist")
                    }

                    account?.email = email
                    view.onNextScreen(RegisterState.SUCCESS, "screen is next", Gson().toJson(account))
                }

                override fun onFailure(call: Call<ResponseModel<List<String>>>, t: Throwable) {
                    dialog.dismiss()
                    view.onCheckMail(RegisterState.EMAIL_EXIST, "email is exist")
                }

            }
        )

    }

    fun validatEmail(email: String) {
        val isEmail = Utils.isEmail(email)
        if (!isEmail) {
            view.onCheckMail(RegisterState.IS_NOT_EMAIL, "this is not a email")
        }else {
            view.onCheckMail(RegisterState.SUCCESS, "email is ok")
        }
    }
}