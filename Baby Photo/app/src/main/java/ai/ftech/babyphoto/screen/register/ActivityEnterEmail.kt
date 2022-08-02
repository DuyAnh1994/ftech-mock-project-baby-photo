package ai.ftech.babyphoto.screen.register

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.RegisterActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_create_pass.*
import kotlinx.android.synthetic.main.activity_enter_email.*
import kotlinx.android.synthetic.main.activity_enter_email.btnRegisterNext2
import kotlinx.android.synthetic.main.activity_enter_email.tieRegisterEmail

class ActivityEnterEmail : AppCompatActivity() {
    private var presenter: EnterEmailPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_email)

        presenter = EnterEmailPresenter(this)

        presenter!!.getAccount()

        ibRegisterBackEmail.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        btnRegisterNext2.setOnClickListener {
            val intent = Intent(this, ActivityCreatePass::class.java)
            startActivity(intent)
        }

        tieRegisterEmail.addTextChangedListener {
            presenter!!.checkEmail(tieRegisterEmail.text.toString())
        }
    }
}