package ai.ftech.babyphoto.screen.register

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.screen.login.AccountLogin
import android.app.Activity
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
            presenter!!.checkPass(
                tieRegisterPass.text.toString(),
                tieRegisterRePass.text.toString()
            )
        }
        //check repass
        tieRegisterRePass.addTextChangedListener {
            presenter!!.checkPass(
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
        stateCheckRePass = state
        tvRegisterWarningPass.setTextColor(Color.parseColor("#DC143C"))
        btnRegisterNext3.setBackgroundResource(R.drawable.selector_rec_gray_color_orange_selected)
        when (state) {
            RegisterState.SUCCESS -> {
                tvRegisterWarningPass.setTextColor(Color.parseColor("#FFFFFF"))
                btnRegisterNext3.setBackgroundResource(R.drawable.selector_rec_orange_color)
            }
            RegisterState.PASS_NOT_VALID -> {
                tvRegisterWarningPass.text = "Passwords are between 6 and 8 characters, do not use special characters"

            }
            RegisterState.PASS_NOT_MATCH -> {
                tvRegisterWarningPass.text = "Confirm password doesn't match"
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
}