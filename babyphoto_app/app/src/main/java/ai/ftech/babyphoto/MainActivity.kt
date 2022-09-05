package ai.ftech.babyphoto

import ai.ftech.babyphoto.screen.login.AccountLoginActivity
import ai.ftech.babyphoto.screen.register.RegisterActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvMainRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        tvMainLogin.setOnClickListener {
            val intent = Intent(this, AccountLoginActivity::class.java)
            startActivity(intent)
        }
    }
}