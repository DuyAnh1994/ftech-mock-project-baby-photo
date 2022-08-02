package ai.ftech.babyphoto.screen.register

import ai.ftech.babyphoto.R
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_create_pass.*
import java.util.regex.Pattern

class ActivityCreatePass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pass)
        ibRegisterBackCreatePass.setOnClickListener {
            val intent = Intent(this, ActivityEnterEmail::class.java)
            startActivity(intent)
        }
        //check pass
        tieRegisterPass.addTextChangedListener {
            if (!isValidPassCharacter(tieRegisterPass.text.toString())||!isValidPassCount(tieRegisterPass.text.toString()))
                tvRegisterWarningPass.setTextColor(Color.parseColor("#DC143C"))
            else
                tvRegisterWarningPass.setTextColor(Color.parseColor("#FFFFFF"))
        }
        //check repass
        tieRegisterRePass.addTextChangedListener {
            if (!isMatchPass(tieRegisterPass.text.toString(), tieRegisterRePass.text.toString())){
                tvRegisterWarningPass.text = "Confirm password doesn't match"
                tvRegisterWarningPass.setTextColor(Color.parseColor("#DC143C"))
                btnRegisterNext3.setBackgroundResource(R.drawable.selector_rec_gray_color_orange_selected)
            }else{
                tvRegisterWarningPass.setTextColor(Color.parseColor("#FFFFFF"))
                btnRegisterNext3.setBackgroundResource(R.drawable.selector_rec_orange_color)
            }
        }
        btnRegisterNext3.setOnClickListener {
//            btnRegisterNext3.setBackgroundColor(Color.parseColor("#DC143C"))
//            Toast.makeText(this@ActivityCreatePass, "click here", Toast.LENGTH_SHORT).show()
            openDialog()
        }
    }
    private fun openDialog() {
        var dialogLoadPass = Dialog(this@ActivityCreatePass)
        dialogLoadPass.setContentView(R.layout.dialog_loading_register_layout)
        dialogLoadPass.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        var appCompatButtonClose : AppCompatButton = dialogLoadPass.findViewById(R.id.btClose)
        appCompatButtonClose.setOnClickListener {
            dialogLoadPass.dismiss()
        }
        dialogLoadPass.show()
    }
    private fun isValidPassCharacter(pass: String): Boolean {
        return Pattern.matches("[a-zA-Z]+", pass)
    }

    private fun isValidPassCount(pass: String): Boolean {
        return pass.length in (6..8)
    }
    private fun isMatchPass(pass: String, rePass: String): Boolean{
        return Pattern.matches(pass, rePass)
    }
}