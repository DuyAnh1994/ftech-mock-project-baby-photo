package ai.ftech.babyphoto.screen.register

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.screen.login.AccountLogin
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_create_pass.*

class ActivityCreatePass() : AppCompatActivity(), ICreatePassContract.View {
    //    private val view = activity
    private var account: Account? = null
    private var presenter: CreatePassPresenter? = null
    private lateinit var stateCheckRePass: RegisterState
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pass)
        val bundle = intent.extras?.let {
            it.apply {
                account = Gson().fromJson(get("account") as String, Account::class.java)
            }
        }
        presenter = CreatePassPresenter(this)

        ibRegisterBackCreatePass.setOnClickListener {
            finish()
        }
        //check pass
        tieRegisterPass.addTextChangedListener {
            presenter!!.checkPass(tieRegisterPass.text.toString())
        }
        //check repass
        tieRegisterRePass.addTextChangedListener {
            presenter!!.checkRePass(
                tieRegisterPass.text.toString(),
                tieRegisterRePass.text.toString()
            )
        }
        btnRegisterNext3.setOnClickListener {
            presenter!!.submit(
                stateCheckRePass,
                tieRegisterPass.text.toString(),
                tieRegisterRePass.text.toString(),
                account
            )
        }
    }


    override fun onInsertAccount(
        state: RegisterState,
        message: String,
        email: String,
    ) {
        val dialog = openDialog()
        when (state) {
            RegisterState.SUCCESS -> {
                dialog.dismiss()
                val intent = Intent(this, AccountLogin::class.java)
                intent.putExtra("Email", email)
                startActivity(intent)
                Log.d("TAG", "onInsertAccount: ${email}")
            }
            RegisterState.PASS_NOT_MATCH -> {
                dialog.dismiss()
                Toast.makeText(this, "Insert failed", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {}
        }
    }

    override fun onCheckPass(state: RegisterState, message: String) {
        when (state) {
            RegisterState.SUCCESS -> {
                tvRegisterWarningPass.setTextColor(Color.parseColor("#DC143C"))
            }
            RegisterState.PASS_NOT_VALID -> {
                tvRegisterWarningPass.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else -> {}
        }

    }

    override fun onCheckRePass(state: RegisterState, message: String) {
        stateCheckRePass = state
        when (state) {
            RegisterState.PASS_NOT_MATCH -> {
                tvRegisterWarningPass.text = "Confirm password doesn't match"
                tvRegisterWarningPass.setTextColor(Color.parseColor("#DC143C"))
                btnRegisterNext3.setBackgroundResource(R.drawable.selector_rec_gray_color_orange_selected)
            }
            RegisterState.SUCCESS -> {
                tvRegisterWarningPass.setTextColor(Color.parseColor("#FFFFFF"))
                btnRegisterNext3.setBackgroundResource(R.drawable.selector_rec_orange_color)
            }
            else -> {}
        }
    }

    fun openDialog(): Dialog {
        var dialogLoadPass = Dialog(this)
        dialogLoadPass.setContentView(R.layout.dialog_loading_register_layout)
        dialogLoadPass.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.btnRegisterNext3.setOnClickListener {
            dialogLoadPass.dismiss()
        }
        dialogLoadPass.show()
        return dialogLoadPass
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