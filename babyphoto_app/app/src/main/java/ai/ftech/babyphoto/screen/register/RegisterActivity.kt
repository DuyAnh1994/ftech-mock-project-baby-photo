package ai.ftech.babyphoto.screen.register

import ai.ftech.babyphoto.MainActivity
import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.screen.register.MultiTextWatcher.TextWatcherWithInstance
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    private var presenterRegister: RegisterPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val edtRegisterFirstName: AppCompatEditText = findViewById(R.id.edtRegisterFirstName)
        val edtRegisterLastName: AppCompatEditText = findViewById(R.id.edtRegisterLastName)
        val btnRegisterNext1: AppCompatButton = findViewById(R.id.btnRegisterNext1)

        presenterRegister = RegisterPresenter(this)

        btnRegisterNext1.setOnClickListener {
            presenterRegister!!.nextScreen()
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

//        ApiService().local().getValue("2", "0").enqueue(
//            object : Callback<Any>{
//                override fun onResponse(call: Call<Any>, response: Response<Any>) {
//                    print(response.body())
//                }
//
//                override fun onFailure(call: Call<Any>, t: Throwable) {
//                    Log.e("ERROR", t.toString())
//                }
//
//            }
//        )
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

