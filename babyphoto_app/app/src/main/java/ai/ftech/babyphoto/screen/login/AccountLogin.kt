package ai.ftech.babyphoto.screen.login

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.screen.home.Home
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_account_login.*
import kotlin.toString as toString

class AccountLogin : AppCompatActivity(), CompoundButton.OnCheckedChangeListener,
    ILoginContract.View {
    private var presenter: AccountLoginPresenter? = null
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var PREF_NAME = "prefs"
    private var KEY_REMEMBER = "remember"
    private var KEY_EMAIL = "email_pref"
    private var KEY_PASS = "pass_pref"
    private var email: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_login)
        var bundle: Bundle? = intent.extras
        email = bundle?.getString("Email")
        presenter = AccountLoginPresenter(this)
        presenter?.getAccount()
        presenter?.checkMailNull(email)
        tieAccountLoginEmail.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        }

        ibAccountLoginBackHome.setOnClickListener {
            val intent = Intent(this, ai.ftech.babyphoto.MainActivity::class.java)
            startActivity(intent)
        }
        acbAccountLogin.setOnClickListener {
            val email = tieAccountLoginEmail.text.toString()
            val password = tieAccountLoginPass.text.toString()
            presenter?.login(email, password)
        }
//        tieAccountLoginPass.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        presenter?.checkValidAccount(
            tieAccountLoginEmail.text.toString().trim(),
            tieAccountLoginPass.text.toString().trim()
        )
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
                    presenter?.checkValidAccount(
                        tieAccountLoginEmail.text.toString(),
                        tieAccountLoginPass.text.toString()
                    )
                }

                override fun onTextChanged(
                    editText: EditText?,
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    presenter?.checkValidAccount(
                        tieAccountLoginEmail.text.toString(),
                        tieAccountLoginPass.text.toString()
                    )
                }

                override fun afterTextChanged(editText: EditText?, editable: Editable?) {
                    presenter?.checkValidAccount(
                        tieAccountLoginEmail.text.toString(),
                        tieAccountLoginPass.text.toString()
                    )
                }

            })
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun managePrefs() {
        if (cbAccountLoginRemember.isChecked) {
            editor?.putString(KEY_EMAIL, tieAccountLoginEmail.text.toString().trim())
            editor?.putString(KEY_PASS, tieAccountLoginPass.text.toString().trim())
            editor?.putBoolean(KEY_REMEMBER, true)
            editor?.apply()
        } else {
            editor?.putBoolean(KEY_REMEMBER, false)
            editor?.remove(KEY_EMAIL)
            editor?.remove(KEY_PASS)
            editor?.apply()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        managePrefs()
    }

    override fun onLogin(state: LoginState, message: String, idaccount: Int) {
        when (state) {
            LoginState.SUCCESS -> {
                val intent = Intent(this, Home::class.java)
                intent.putExtra("idaccount", idaccount)
                startActivity(intent)
                finish()
            }
            LoginState.EMAIL_OR_PASS_EMPTY -> {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
            LoginState.EMAIL_NOT_FOUND -> {
                showBottomSheet()
            }
            else -> {}
        }
    }

    override fun onValidAccount(state: LoginState, message: String) {
        when (state) {
            LoginState.SUCCESS -> {
                tvAccountLoginWarning.visibility = View.INVISIBLE
                acbAccountLogin.setBackgroundResource(R.drawable.selector_rec_orange_color_correct_login)
            }
            LoginState.EMAIL_NOT_FOUND -> {
                tvAccountLoginWarning.visibility = View.VISIBLE
                acbAccountLogin.setBackgroundResource(R.drawable.selector_rec_orange_color_correct_login)
            }
            LoginState.EMAIL_NOT_FOUND_OR_EMPTY -> {
                tvAccountLoginWarning.visibility = View.VISIBLE
                acbAccountLogin.setBackgroundResource(R.drawable.selector_rec_gray_incorrect_login)
            }
            else -> {}
        }
    }

    override fun onCheckMailNull(state: LoginState, message: String) {
        when (state) {
            LoginState.MAIL_NULL -> {
                sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                editor = sharedPreferences?.edit()
                cbAccountLoginRemember.isChecked =
                    sharedPreferences!!.getBoolean(KEY_REMEMBER, false)//check is_checked
                tieAccountLoginEmail.setText(sharedPreferences?.getString(KEY_EMAIL, ""))
                tieAccountLoginPass.setText(sharedPreferences?.getString(KEY_PASS, ""))
                cbAccountLoginRemember.setOnCheckedChangeListener(this)
            }
            LoginState.MAIL_NOT_NULL -> {
                tieAccountLoginEmail.setText(email.toString())
                tieAccountLoginPass.setText("")
            }
            else -> {}
        }
    }

    fun showBottomSheet(): Dialog {
        var dialog = Dialog(this)
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
                callback?.beforeTextChanged(editText, s, start, count, after)
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                callback?.onTextChanged(editText, s, start, before, count)
            }

            override fun afterTextChanged(editable: Editable) {
                callback?.afterTextChanged(editText, editable)
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