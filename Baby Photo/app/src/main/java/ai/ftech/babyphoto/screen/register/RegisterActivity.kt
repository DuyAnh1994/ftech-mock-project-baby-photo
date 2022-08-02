package ai.ftech.babyphoto

import ai.ftech.babyphoto.MultiTextWatcher.TextWatcherWithInstance
import ai.ftech.babyphoto.base.service.ApiService
import ai.ftech.babyphoto.screen.register.ActivityEnterEmail
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        var edtRegisterFirstName: AppCompatEditText = findViewById(R.id.edtRegisterFirstName)
        var edtRegisterLastName: AppCompatEditText = findViewById(R.id.edtRegisterLastName)
        var btnRegisterNext1: AppCompatButton = findViewById(R.id.btnRegisterNext1)
        btnRegisterNext1.isClickable = false
        btnRegisterNext1.setOnClickListener {
            if (btnRegisterNext1.isClickable){
                val intent = Intent(this, ActivityEnterEmail::class.java)
                startActivity(intent)
            }
        }
        MultiTextWatcher()
            .registerEditText(edtRegisterFirstName)
            .registerEditText(edtRegisterLastName)
            .setCallback(object : TextWatcherWithInstance {
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
                    if (edtRegisterFirstName.text.toString() != "" && edtRegisterLastName.text.toString() != ""
                        && isValidName(
                            edtRegisterFirstName.text.toString()
                        ) && isValidName(edtRegisterLastName.text.toString())
                    ) {
                        btnRegisterNext1.isClickable = true
                        btnRegisterNext1.setBackgroundResource(R.drawable.selector_rec_orange_color)
                    } else {
                        btnRegisterNext1.setBackgroundResource(R.drawable.selector_rec_gray_color_orange_selected)
                    }
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

    fun isValidName(name: String?): Boolean {
        return Pattern.matches("[a-zA-Z]+", name)
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

