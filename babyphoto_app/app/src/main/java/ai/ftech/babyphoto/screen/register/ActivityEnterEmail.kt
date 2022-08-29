package ai.ftech.babyphoto.screen.register

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.Utils
import ai.ftech.babyphoto.model.Account
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_enter_email.*
import kotlinx.android.synthetic.main.activity_enter_email.btnRegisterNext2

class ActivityEnterEmail : AppCompatActivity(), IEnterEmailContract.View{
    private var presenter: EnterEmailPresenter? = null
    private var account: Account?= null
    private var stateCheckMail: RegisterState? = null
    private var textEmail = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_email)
        val bundle = intent.extras.let {
            it?.apply {
                account = Gson().fromJson(get("account") as String, Account::class.java)
            }
        }
        var tieRegisterEmail:TextInputEditText = findViewById(R.id.tieRegisterEmail)
        presenter = EnterEmailPresenter(this)

        ibRegisterBackEmail.setOnClickListener {
            finish()
        }
        btnRegisterNext2.setOnClickListener {
            val dialog = Utils().loading(this)
            presenter!!.checkEmail(dialog, tieRegisterEmail.text.toString(), account)
        }

        tieRegisterEmail.addTextChangedListener {
            presenter!!.validatEmail(tieRegisterEmail.text.toString())
        }
    }

    override fun onCheckMail(state: RegisterState, message: String) {
        stateCheckMail = state
        tvRegisterWarningEmail.visibility = View.VISIBLE
        when(state){
            RegisterState.SUCCESS ->{
                tvRegisterWarningEmail.visibility = View.INVISIBLE
                btnRegisterNext2.setBackgroundResource(R.drawable.selector_rec_orange_color)
            }
            RegisterState.EMAIL_EXIST ->{
                tvRegisterWarningEmail.text = "This email is wrong or already exists, please enter a new email"
            }
            RegisterState.IS_NOT_EMAIL ->{
                tvRegisterWarningEmail.text = "This is not email"
            }
            RegisterState.EMAIL_EXIST_OR_NOT_EMAIL ->{
                btnRegisterNext2.setBackgroundResource(R.drawable.selector_rec_gray_color_orange_selected)
            }
            else -> {}
        }
    }

    override fun onNextScreen(state: RegisterState, message: String, account: String) {
        when(state){
            RegisterState.SUCCESS->{
                val intent = Intent(this, ActivityCreatePass::class.java)
                intent.putExtra("account", account)
                startActivity(intent)
            }
            else -> {}
        }
    }
}