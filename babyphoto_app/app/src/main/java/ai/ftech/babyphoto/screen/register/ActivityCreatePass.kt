package ai.ftech.babyphoto.screen.register

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.model.Account
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_create_pass.*

class ActivityCreatePass() : AppCompatActivity() {
//    private val view = activity
    private var account: Account? = null
    private var presenter : CreatePassPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pass)
        presenter = CreatePassPresenter(this)

        ibRegisterBackCreatePass.setOnClickListener {
            finish()
        }
        //check pass
        tieRegisterPass.addTextChangedListener {
            presenter!!.checkPass(tieRegisterPass.text.toString())
        }
        //check repass
        tieRegisterRePass.addTextChangedListener {
            presenter!!.checkRePass(tieRegisterPass.text.toString(), tieRegisterRePass.text.toString())
        }
        btnRegisterNext3.setOnClickListener {
            presenter!!.submit()
        }
    }

//    private fun openDialog(): Dialog {
//        var dialogLoadPass = Dialog(this@ActivityCreatePass)
//        dialogLoadPass.setContentView(R.layout.dialog_loading_register_layout)
//        dialogLoadPass.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        var appCompatButtonClose: AppCompatButton = dialogLoadPass.findViewById(R.id.btClose)
//        appCompatButtonClose.setOnClickListener {
//            dialogLoadPass.dismiss()
//        }
//        dialogLoadPass.show()
//        return dialogLoadPass
//    }
//
//    private fun isMatchPass(pass: String, rePass: String): Boolean {
//        return Pattern.matches(pass, rePass)
//    }

//    fun submit() {
//        if (!isMatchPass(tieRegisterPass.text.toString(), tieRegisterRePass.text.toString())) return
//
//        account?.password = tieRegisterPass.text.toString()
//
//        print(Gson().toJson(account))
//
//        val dialog = openDialog()
//
//        ApiService().base().insertAccount(
//            account!!.email,
//            account!!.password,
//            account!!.firstname,
//            account!!.lastname,
//            account!!.idaccount
//        ).enqueue(
//            object : Callback<String> {
//                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    dialog.dismiss()
//                    Toast.makeText(
//                        this@ActivityCreatePass.applicationContext,
//                        response.body().toString(),
//                        Toast.LENGTH_SHORT
//                    ).show()
////                    val intent = Intent(view, MainActivity::class.java)
////                    startActivity(intent)
//                }
//
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                    dialog.dismiss()
//                    Toast.makeText(this@ActivityCreatePass.applicationContext, "Insert failed", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//        )
//    }
}