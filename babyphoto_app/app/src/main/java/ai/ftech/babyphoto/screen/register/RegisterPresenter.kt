package ai.ftech.babyphoto.screen.register

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.screen.register.RegisterActivity
import ai.ftech.babyphoto.base.Utils
import ai.ftech.babyphoto.model.Account
import android.content.Intent
import android.graphics.Color
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.random.Random

class RegisterPresenter(activity: RegisterActivity) {
    private val view = activity
    fun checkName(name: String?, name2: String?): Boolean {
        val isName = Utils().isValidName(name, name2)
        val isNull = Utils().checkNull(name, name2)
        if (isName && !isNull) {
            view.btnRegisterNext1.setBackgroundResource(R.drawable.selector_rec_orange_color)
            view.tvRegisterWarning.setTextColor(Color.parseColor("#66000000"))
        }else {
            view.btnRegisterNext1.setBackgroundResource(R.drawable.selector_rec_gray_color_orange_selected)
            view.tvRegisterWarning.setTextColor(Color.parseColor("#FF4B4B"))
        }
        return isName && !isNull
    }

    fun nextScreen(){
        if (!checkName(
                view.edtRegisterFirstName.text.toString(),
                view.edtRegisterLastName.text.toString()
            )) return
        val account = Account(
            view.edtRegisterFirstName.text.toString(),
            view.edtRegisterLastName.text.toString(),
            "",
            "",
            Random.nextInt(10000)
        )

        val intent = Intent(view, ActivityEnterEmail::class.java)
        intent.putExtra("account", Gson().toJson(account))
        view.startActivity(intent)
    }
}