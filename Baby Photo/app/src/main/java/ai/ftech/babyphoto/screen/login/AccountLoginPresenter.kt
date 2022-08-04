package ai.ftech.babyphoto.screen.login

import ai.ftech.babyphoto.HomeTest
import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.Utils
import ai.ftech.babyphoto.base.service.ApiService
import ai.ftech.babyphoto.model.AccountModel
import ai.ftech.babyphoto.screen.register.ActivityCreatePass
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_account_login.*
import kotlinx.android.synthetic.main.activity_enter_email.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountLoginPresenter(activity: AccountLogin) {
    private val view = activity
    private val apiService = ApiService().base()
    private var lAccount = mutableListOf<AccountModel>()

    fun getAccount() {
        apiService.account().enqueue(
            object : Callback<List<AccountModel>> {
                override fun onResponse(
                    call: Call<List<AccountModel>>,
                    response: Response<List<AccountModel>>
                ) {
                    if (response.body() != null) {
                        lAccount = response.body() as MutableList<AccountModel>
                        Toast.makeText(view, "Get data success", Toast.LENGTH_SHORT).show()
                        return
                    }
                    Toast.makeText(view, "Data is empty", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<List<AccountModel>>, t: Throwable) {
                    Toast.makeText(view, "Get data failed", Toast.LENGTH_SHORT).show()
                    Log.e("ERROR", t.toString())
                }

            }
        )
    }

    fun login() {
        val email = view.tieAccountLoginEmail.text.toString()
        val password = view.tieAccountLoginPass.text.toString()
        if (email.trim().isEmpty() || password.trim().isEmpty()) return
        if (lAccount.any { account: AccountModel -> account.email == email && account.password == password }) {
            val intent = Intent(view, HomeTest::class.java)
            view.startActivity(intent)
            view.finish()
        } else {
            Log.d("TAG", "login: fail")

        }
    }

    fun checkValidAccount(email: String, pass: String): Boolean {
        val isValid =
            lAccount.any { account: AccountModel -> account.email == email && account.password == pass }
        if (!isValid) {
            view.tvAccountLoginWarning.visibility = View.VISIBLE
            view.acbAccountLogin.setBackgroundResource(R.drawable.selector_rec_gray_color_orange_selected)
        } else {
            view.tvAccountLoginWarning.visibility = View.INVISIBLE
            view.acbAccountLogin.setBackgroundResource(R.drawable.selector_rec_orange_color)
        }
        return !isValid
    }
//    fun nextScreen(){
//        if (checkValidAccount(view.tieAccountLoginEmail.text.toString(), view.tieAccountLoginPass.text.toString())) return
//        val intent = Intent(view, ActivityCreatePass::class.java)
//        view.startActivity(intent)
//    }
}