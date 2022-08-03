package ai.ftech.babyphoto.screen.register

import ai.ftech.babyphoto.MainActivity
import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.service.ApiService
import ai.ftech.babyphoto.model.AccountModel
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.addTextChangedListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_create_pass.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class ActivityCreatePass() : AppCompatActivity() {
//    private val view = activity
    private var account: AccountModel? = null
    private var presenter : CreatePassPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pass)
        presenter = CreatePassPresenter(this)

//        intent.extras?.let {
//            it.apply {
//                account = Gson().fromJson(get("account") as String, AccountModel::class.java)
//            }
//        }

        ibRegisterBackCreatePass.setOnClickListener {
            finish()
        }
        //check pass
        tieRegisterPass.addTextChangedListener {
            presenter!!.checkPass(tieRegisterPass.text.toString())
        }
        //check repass
        tieRegisterRePass.addTextChangedListener {
            presenter!!.checkRePass(tieRegisterPass.text.toString(), tieRegisterRePass.text.toString())
        }
        btnRegisterNext3.setOnClickListener {
            presenter!!.submit()
        }
    }

//    private fun openDialog(): Dialog {
//        var dialogLoadPass = Dialog(this@ActivityCreatePass)
//        dialogLoadPass.setContentView(R.layout.dialog_loading_register_layout)
//        dialogLoadPass.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        var appCompatButtonClose: AppCompatButton = dialogLoadPass.findViewById(R.id.btClose)
//        appCompatButtonClose.setOnClickListener {
//            dialogLoadPass.dismiss()
//        }
//        dialogLoadPass.show()
//        return dialogLoadPass
//    }
//
//    private fun isMatchPass(pass: String, rePass: String): Boolean {
//        return Pattern.matches(pass, rePass)
//    }

//    fun submit() {
//        if (!isMatchPass(tieRegisterPass.text.toString(), tieRegisterRePass.text.toString())) return
//
//        account?.password = tieRegisterPass.text.toString()
//
//        print(Gson().toJson(account))
//
//        val dialog = openDialog()
//
//        ApiService().base().insertAccount(
//            account!!.email,
//            account!!.password,
//            account!!.firstname,
//            account!!.lastname,
//            account!!.idaccount
//        ).enqueue(
//            object : Callback<String> {
//                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    dialog.dismiss()
//                    Toast.makeText(
//                        this@ActivityCreatePass.applicationContext,
//                        response.body().toString(),
//                        Toast.LENGTH_SHORT
//                    ).show()
////                    val intent = Intent(view, MainActivity::class.java)
////                    startActivity(intent)
//                }
//
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                    dialog.dismiss()
//                    Toast.makeText(this@ActivityCreatePass.applicationContext, "Insert failed", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//        )
//    }
}