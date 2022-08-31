package ai.ftech.babyphoto.screen.register

import ai.ftech.babyphoto.MainActivity
import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.screen.register.MultiTextWatcher.TextWatcherWithInstance
import ai.ftech.babyphoto.screen.test.TestActivity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity(), IRegisterContract.View {
    private var presenterRegister: RegisterPresenter? = null
    private lateinit var stateCheckName: RegisterState
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val edtRegisterFirstName: AppCompatEditText = findViewById(R.id.edtRegisterFirstName)
        val edtRegisterLastName: AppCompatEditText = findViewById(R.id.edtRegisterLastName)
        val btnRegisterNext1: AppCompatButton = findViewById(R.id.btnRegisterNext1)

        presenterRegister = RegisterPresenter(this)

        btnRegisterNext1.setOnClickListener {
            presenterRegister!!.nextScreen(stateCheckName, edtRegisterFirstName.text.toString(), edtRegisterLastName.text.toString())
        }
        ibRegisterBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        MultiTextWatcher()
            .registerEditText(edtRegisterFirstName)
            .registerEditText(edtRegisterLastName)
            .setCallback(callback = object : TextWatcherWithInstance {
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
                    presenterRegister!!.checkName(
                        edtRegisterFirstName.text.toString(),
                        edtRegisterLastName.text.toString()
                    )
                }

            })
    }

    override fun onCheckName(state: RegisterState, message: String) {
        stateCheckName = state
        when(state){
            RegisterState.SUCCESS ->{
                btnRegisterNext1.setBackgroundResource(R.drawable.selector_rec_orange_color)
                tvRegisterWarning.setTextColor(Color.parseColor("#66000000"))
            }
            RegisterState.NAME_NOT_VALID ->{
                btnRegisterNext1.setBackgroundResource(R.drawable.selector_rec_gray_color_orange_selected)
                tvRegisterWarning.setTextColor(Color.parseColor("#FF4B4B"))
            }
            else -> {}
        }
    }

    override fun onNextScreen(state: RegisterState, message: String, account: String) {
        when(state){
            RegisterState.SUCCESS ->{
                val intent = Intent(this, ActivityEnterEmail::class.java)
                intent.putExtra("account", account)
                startActivity(intent)
            }
            RegisterState.NAME_NOT_VALID ->{
//                val intent = Intent(this, TestActivity::class.java)
//                startActivity(intent)
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
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

