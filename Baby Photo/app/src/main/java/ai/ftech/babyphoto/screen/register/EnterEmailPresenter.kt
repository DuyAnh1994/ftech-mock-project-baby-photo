package ai.ftech.babyphoto.screen.register

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.Utils
import ai.ftech.babyphoto.base.service.ApiService
import ai.ftech.babyphoto.model.AccountModel
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

class EnterEmailPresenter(activity: ActivityEnterEmail) {
    private  val view = activity

    private var account: AccountModel?= null
    private val bundle = view.intent.extras.let {
        it?.apply {
            account = Gson().fromJson(get("account") as String, AccountModel::class.java)
        }
    }

    //khai báo service
    private val apiService = ApiService().base()
    private var lAccount = mutableListOf<AccountModel>()

    //hàm gọi lấy danh sách tài khoản
    fun getAccount() {
        apiService.account().enqueue(
            object : Callback<List<AccountModel>> {
                override fun onResponse(
                    call: Call<List<AccountModel>>,
                    response: Response<List<AccountModel>>
                ) {
                    if (response.body() != null){
                        lAccount = response.body() as MutableList<AccountModel>
                        Toast.makeText(view, "Get data success", Toast.LENGTH_SHORT).show()
                        return
                    }
                    Toast.makeText(view, "Data is empty", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<List<AccountModel>>, t: Throwable) {
                    Toast.makeText(view, "Get data failed", Toast.LENGTH_SHORT).show()
                    Log.e("ERROR",t.toString())
                }

            }
        )
    }

    fun checkEmail(email: String): Boolean{
        val hasEmail = lAccount.any { accountModel: AccountModel ->  accountModel.email == email}
        val isEmail = Utils().isEmail(email)

        if (hasEmail){
            view.tvRegisterWarningEmail.text = "This email is wrong or already exists, please enter a new email"
        }

        if (!isEmail){
            view.tvRegisterWarningEmail.text = "This is not email"
        }

        if(hasEmail || !isEmail){
            view.tvRegisterWarningEmail.visibility = View.VISIBLE
            view.btnRegisterNext2.setBackgroundResource(R.drawable.selector_rec_gray_color_orange_selected)
        }else{
            view.tvRegisterWarningEmail.visibility = View.INVISIBLE
            view.btnRegisterNext2.setBackgroundResource(R.drawable.selector_rec_orange_color)
        }

        return hasEmail || !isEmail
    }

    fun nextScreen(){
        if (checkEmail(view.tieRegisterEmail.text.toString())) return

        account?.email = view.tieRegisterEmail.text.toString()

        val intent = Intent(view, ActivityCreatePass::class.java)
        intent.putExtra("account", Gson().toJson(account))
        view.startActivity(intent)
    }
}