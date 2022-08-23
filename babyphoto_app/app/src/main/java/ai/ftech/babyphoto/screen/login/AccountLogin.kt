package ai.ftech.babyphoto.screen.login

import ai.ftech.babyphoto.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_account_login.*
import kotlin.toString as toString

class AccountLogin : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {
    private var presenter: AccountLoginPresenter? = null
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var PREF_NAME = "prefs"
    private var KEY_REMEMBER = "remember"
    private var KEY_EMAIL = "email_pref"
    private var KEY_PASS = "pass_pref"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_login)
        var bundle: Bundle? = intent.extras
        var email  = bundle?.get("Email")
        if (email== null){

        }else{
            tieAccountLoginEmail.setText(email.toString())
        }
        tieAccountLoginEmail.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        }
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        if (sharedPreferences!!.getBoolean(KEY_REMEMBER, false))
            cbAccountLoginRemember.isChecked
        else
            !cbAccountLoginRemember.isChecked
        tieAccountLoginEmail.setText(sharedPreferences!!.getString(KEY_EMAIL,""))
        tieAccountLoginPass.setText(sharedPreferences!!.getString(KEY_PASS,""))
        cbAccountLoginRemember.setOnCheckedChangeListener(this)


        presenter = AccountLoginPresenter(this)

        presenter!!.getAccount()

        ibAccountLoginBackHome.setOnClickListener {
            val intent = Intent(this, ai.ftech.babyphoto.MainActivity::class.java)
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

                }

                override fun onTextChanged(
                    editText: EditText?,
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                }

                override fun afterTextChanged(editText: EditText?, editable: Editable?) {
                    presenter!!.checkValidAccount(tieAccountLoginEmail.text.toString(), tieAccountLoginPass.text.toString())
                }

            })
    }
    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    private  fun managePrefs(){
        if (cbAccountLoginRemember.isChecked){
            editor!!.putString(KEY_EMAIL, tieAccountLoginEmail.text.toString().trim())
            editor!!.putString(KEY_PASS, tieAccountLoginPass.text.toString().trim())
            editor!!.putBoolean(KEY_REMEMBER, true)
            editor!!.apply()
        }else{
            editor!!.putBoolean(KEY_REMEMBER, false)
            editor!!.remove(KEY_EMAIL)
            editor!!.remove(KEY_PASS)
            editor!!.apply()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        managePrefs()
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