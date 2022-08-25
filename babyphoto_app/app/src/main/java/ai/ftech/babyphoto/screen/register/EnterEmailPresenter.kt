package ai.ftech.babyphoto.screen.register

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.Utils
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.model.ResponseModel
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_enter_email.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnterEmailPresenter(private var view: IEnterEmailContract.View) {
    private var account: Account?= null

    //khai báo service
    private val apiService = APIService.base()
    private var lAccount = listOf<Account>()

    //hàm gọi lấy danh sách tài khoản
    fun getAccount() {
        apiService.account().enqueue(
            object : Callback<ResponseModel<List<Account>>> {
                override fun onResponse(
                    call: Call<ResponseModel<List<Account>>>,
                    response: Response<ResponseModel<List<Account>>>
                ) {
                    if (response.body() != null){
                        lAccount = response.body()!!.data
                        return
                    }
                }

                override fun onFailure(call: Call<ResponseModel<List<Account>>>, t: Throwable) {
                    Log.e("ERROR",t.toString())
                }

            }
        )
    }

    fun checkEmail(email: String){
        val hasEmail = lAccount.any { accountModel: Account ->  accountModel.email == email}
        val isEmail = Utils().isEmail(email)

        if (hasEmail){
            view.onCheckMail(RegisterState.EMAIL_EXIST, "email is exist")
        }

        if (!isEmail){
            view.onCheckMail(RegisterState.IS_NOT_EMAIL, "this is not a email")
        }

        if(hasEmail || !isEmail){
           view.onCheckMail(RegisterState.EMAIL_EXIST_OR_NOT_EMAIL, "emai not exist or not email")
        }else{
            view.onCheckMail(RegisterState.SUCCESS, "email is ok")
        }

    }

    fun nextScreen(state: RegisterState, email: String, accountN: Account?){
        account = accountN
        when(state){
            RegisterState.SUCCESS->{
                account?.email = email
                view.onNextScreen(RegisterState.SUCCESS, "screen is next", Gson().toJson(account))
            }
            else -> {}
        }
    }
}