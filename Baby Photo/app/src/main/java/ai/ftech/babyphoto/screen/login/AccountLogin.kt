package ai.ftech.babyphoto.screen.login

import ai.ftech.babyphoto.MainActivity
import ai.ftech.babyphoto.R
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.lang.UCharacter.IndicPositionalCategory.BOTTOM
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window.FEATURE_NO_TITLE
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_account_login.*
import kotlinx.android.synthetic.main.activity_create_pass.*

class AccountLogin : AppCompatActivity() {
    private var presenter: AccountLoginPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_login)

        presenter = AccountLoginPresenter(this)

        presenter!!.getAccount()

        ibAccountLoginBackHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        acbAccountLogin.setOnClickListener {
            presenter!!.login()
        }
        ai.ftech.babyphoto.screen.register.MultiTextWatcher()
            .registerEditText(tieAccountLoginEmail)
            .registerEditText(tieAccountLoginPass)
            .setCallback(callback = object :
                ai.ftech.babyphoto.screen.register.MultiTextWatcher.TextWatcherWithInstance {
                override fun beforeTextChanged(
                    editText: EditText?,
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // TODO: Do some thing with editText
                }

                override fun onTextChanged(
                    editText: EditText?,
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    // TODO: Do some thing with editText
                }

                override fun afterTextChanged(editText: EditText?, editable: Editable?) {
                    presenter!!.checkValidAccount(tieAccountLoginEmail.text.toString(), tieAccountLoginPass.text.toString())
                }

            })

    }



}
class MultiTextWatcher {
    private var callback: TextWatcherWithInstance? = null
    fun setCallback(callback: TextWatcherWithInstance?): MultiTextWatcher {
        this.callback = callback
        return this
    }

    fun registerEditText(editText: EditText): MultiTextWatcher {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                callback!!.beforeTextChanged(editText, s, start, count, after)
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                callback!!.onTextChanged(editText, s, start, before, count)
            }

            override fun afterTextChanged(editable: Editable) {
                callback!!.afterTextChanged(editText, editable)
            }
        })
        return this
    }

    interface TextWatcherWithInstance {
        fun beforeTextChanged(
            editText: EditText?,
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        )

        fun onTextChanged(
            editText: EditText?,
            s: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        )

        fun afterTextChanged(editText: EditText?, editable: Editable?)
    }
}