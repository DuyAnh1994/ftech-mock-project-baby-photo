package ai.ftech.babyphoto.screen.login

import ai.ftech.babyphoto.HomeTest
import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.Utils
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Account
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_account_login.*
import kotlinx.android.synthetic.main.activity_enter_email.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountLoginPresenter(activity: AccountLogin) {
    private val view = activity
    private val apiService = APIService().base()
    private var lAccount = mutableListOf<Account>()

    fun getAccount() {
        apiService.account().enqueue(
            object : Callback<List<Account>> {
                override fun onResponse(
                    call: Call<List<Account>>,
                    response: Response<List<Account>>
                ) {
                    if (response.body() != null) {
                        lAccount = response.body() as MutableList<Account>
                        Toast.makeText(view, "Get data success", Toast.LENGTH_SHORT).show()
                        return
                    }
                    Toast.makeText(view, "Data is empty", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<List<Account>>, t: Throwable) {
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
        if (lAccount.any { account: Account -> account.email == email && account.password == password }) {
            val intent = Intent(view, HomeTest::class.java)
            view.startActivity(intent)
            view.finish()
        } else {
            showBottomSheet()
        }
    }

    fun checkValidAccount(email: String, pass: String): Boolean {
        val isValid =
            lAccount.any { account: Account -> account.email == email && account.password == pass }//mk correct
        val isValid2 = Utils().checkNull(email, pass)// true->null
        if (!isValid2 && isValid) {
            view.tvAccountLoginWarning.visibility = View.INVISIBLE
            view.acbAccountLogin.setBackgroundResource(R.drawable.selector_rec_orange_color)
        } else if (!isValid2 && !isValid){
            view.tvAccountLoginWarning.visibility = View.VISIBLE
            view.acbAccountLogin.setBackgroundResource(R.drawable.selector_rec_orange_color)
        }else{
            view.tvAccountLoginWarning.visibility = View.VISIBLE
            view.acbAccountLogin.setBackgroundResource(R.drawable.selector_rec_gray_color_orange_selected)
        }
        return !isValid2 && isValid

    }

    fun showBottomSheet(): Dialog {
//        var bottomSheetDialog = BottomSheetDialog(this)
//        bottomSheetDialog.setContentView(R.layout.password_recovery_bottomsheet_layout)
//        bottomSheetDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        bottomSheetDialog.show()
        var dialog = Dialog(view)
        dialog.setContentView(R.layout.password_recovery_bottomsheet_layout)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.window?.setGravity(Gravity.BOTTOM)
        return dialog
    }
//    fun nextScreen(){
//        if (checkValidAccount(view.tieAccountLoginEmail.text.toString(), view.tieAccountLoginPass.text.toString())) return
//        val intent = Intent(view, ActivityCreatePass::class.java)
//        view.startActivity(intent)
//    }
}