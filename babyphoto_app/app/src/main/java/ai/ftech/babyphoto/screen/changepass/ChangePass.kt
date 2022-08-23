package ai.ftech.babyphoto.screen.changepass

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Account
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_change_pass.*
import kotlinx.android.synthetic.main.activity_detail_account.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePass : AppCompatActivity() {
    private var presenter : ChangePassPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)
        presenter = ChangePassPresenter(this)

        presenter!!.getAccountId()
        tieCurrentPass.addTextChangedListener {
            presenter!!.checkPass(tieCurrentPass.text.toString())
            presenter!!.checkNull(tieCurrentPass.text.toString(),tieNewPass.text.toString(),tieReNewPass.text.toString())
        }
        tieNewPass!!.addTextChangedListener {
            presenter!!.checkNewPass(tieNewPass.text.toString())
            presenter!!.checkNull(tieCurrentPass.text.toString(),tieNewPass.text.toString(),tieReNewPass.text.toString())
        }
        tieReNewPass!!.addTextChangedListener {
            presenter!!.checkReNewPass(tieNewPass.text.toString(),tieReNewPass.text.toString())
            presenter!!.checkNull(tieCurrentPass.text.toString(),tieNewPass.text.toString(),tieReNewPass.text.toString())
        }
        tvSaveChange.setOnClickListener {
            presenter!!.submit()
        }
        ibChangePassBack.setOnClickListener {
            finish()
        }

    }
}