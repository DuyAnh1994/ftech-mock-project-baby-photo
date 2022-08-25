package ai.ftech.babyphoto.screen.login

import ai.ftech.babyphoto.base.Constant
import ai.ftech.babyphoto.base.Utils
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.model.ResponseModel
import android.content.SharedPreferences
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountLoginPresenter(private var view: ILoginContract.View) {

    private val apiService = APIService.base()
    private var lAccount = mutableListOf<Account>()
    private var index1: Int = 0
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    fun getAccount() {
        apiService.account().enqueue(
            object : Callback<ResponseModel<List<Account>>> {
                override fun onResponse(
                    call: Call<ResponseModel<List<Account>>>,
                    response: Response<ResponseModel<List<Account>>>
                ) {
                    if (response.body() != null) {
                        response.body()!!.data.also { lAccount = it as MutableList<Account> }
                        return
                    }
                }

                override fun onFailure(call: Call<ResponseModel<List<Account>>>, t: Throwable) {
                    Log.e("ERROR", t.toString())
                }

            }
        )
    }

    fun login(email: String, password: String) {

        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            view.onLogin(LoginState.EMAIL_OR_PASS_EMPTY, "email, pass is empty", -1)
        } else {
            val account: Account? = lAccount.find { password == it.password && email == it.email }

            if (account != null) {
                Constant.account = account
                view.onLogin(LoginState.SUCCESS, "email, pass is valid", lAccount[index1].idaccount)
            } else {
                view.onLogin(LoginState.EMAIL_NOT_FOUND, "email, pass is not found", -1)
            }
        }
    }

    fun checkMailNull(email: String?) {
        if (email == null) {
            view.onCheckMailNull(LoginState.MAIL_NULL, "emai is null")

        } else {
            view.onCheckMailNull(LoginState.MAIL_NOT_NULL, "email is not null")
        }
    }

    fun checkValidAccount(email: String, pass: String) {
        val isValid =
            lAccount.any { account: Account -> account.email == email && account.password == pass }//mk correct
        val isValid2 = Utils().checkNull(email, pass)// true->null
        if (!isValid2 && isValid) {
            view.onValidAccount(LoginState.SUCCESS, "email, pass is valid", lAccount.isNotEmpty())
        } else if (!isValid2 && !isValid) {
            view.onValidAccount(LoginState.EMAIL_NOT_FOUND, "email, pass is not found", lAccount.isNotEmpty())
        } else {
            view.onValidAccount(
                LoginState.EMAIL_NOT_FOUND_OR_EMPTY,
                "email, pass is not found or empty",
                lAccount.isNotEmpty()
            )
        }
    }


//    fun hideKeyboard(view: View) {
//        val inputMethodManager = getSystemService(view.context.applicationContext.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
//    }
//    fun nextScreen(){
//        if (checkValidAccount(view.tieAccountLoginEmail.text.toString(), view.tieAccountLoginPass.text.toString())) return
//        val intent = Intent(view, ActivityCreatePass::class.java)
//        view.startActivity(intent)
//    }
}