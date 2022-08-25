package ai.ftech.babyphoto.screen.detailaccount


import ai.ftech.babyphoto.MainActivity
import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.Constant
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.model.AccountUpdate
import ai.ftech.babyphoto.screen.changepass.ChangePass
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.internal.ContextUtils
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail_account.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailAccount : AppCompatActivity(), IDetailAccountContract.View {
    private var presenter: DetailAccountPresenter? = null
    private var email: String = ""
    private var firstname: String = ""
    private var lastname: String = ""
    private var index1: Int = 0
    private var idaccount1: Int = 0
    private var account: AccountUpdate = AccountUpdate("", "", "", 0)

    //    private var mutableListAccount: MutableList<AccountUpdate> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ai.ftech.babyphoto.R.layout.activity_detail_account)
        //val bundle: Bundle? = intent.extras
        //var idaccount = bundle?.getInt("idaccount")
        //val showTop = bundle?.get("showTop")
        presenter = DetailAccountPresenter(this)

        //presenter!!.getAccount(idaccount)

        onGetAccount()

        tvCoppyIDAccount.setOnClickListener {
           coppyClipboardManager()
        }
        ibAccountDetailBack.setOnClickListener {
            finish()
        }
        llAccountDetailSaveChange.setOnClickListener {
            account.email = edtViewEmailAccountDetail.text.toString()
            account.firstname = edtAccountDetailName.text.toString()
            account.lastname = edtAccountDetailNameLast.text.toString()
            account.idaccount = Constant.account.idaccount

            presenter!!.updateAccount(account)
        }
        llAccountDetailLogout.setOnClickListener {
            openDialog()
        }
        clProfileChangePass.setOnClickListener {
            var intent = Intent(this, ChangePass::class.java)
            //intent.putExtra("idaccount", idaccount as Int)
            startActivity(intent)
        }
        Snackbar.make(
            findViewById(ai.ftech.babyphoto.R.id.llAccountDetailLogout),
            ai.ftech.babyphoto.R.string.add_baby,
            Snackbar.LENGTH_SHORT
        ).show()

    }

    fun onGetAccount() {
        email = Constant.account.email
        firstname = Constant.account.firstname
        lastname = Constant.account.lastname
        idaccount1 = Constant.account.idaccount
        tvViewIDAccountDetail.text = Constant.account.idaccount.toString()
        edtAccountDetailName.setText(Constant.account.firstname)
        edtAccountDetailNameLast.setText(Constant.account.lastname)
        edtViewEmailAccountDetail.setText(Constant.account.email)

    }

    override fun onUpdateAccount(state: DetailAccountState, message: String) {
        showSnackbar(message)
        when (state) {
            DetailAccountState.SUCCESS -> {

            }
            DetailAccountState.UPDATE_ACCOUNT_FAIL -> {}
            else -> {}
        }
    }
    @SuppressLint("RestrictedApi")
    fun coppyClipboardManager() {
        val stringYouExtracted: String = tvViewIDAccountDetail.text.toString()
        val clipboard =
            ContextUtils.getActivity(this)!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", stringYouExtracted)

        clipboard.setPrimaryClip(clip)
        showSnackbar("ID copied")
        Toast.makeText(
            ContextUtils.getActivity(this),
            "Copy coupon code copied to clickboard!",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    fun showSnackbar(content: String) {
        val mSnackBar = Snackbar.make(this.detailAcccountMain, content, Snackbar.LENGTH_LONG)
//        mSnackBar.setAction("close", View.OnClickListener {
//
//        })
            .setActionTextColor(Color.parseColor("#FFFFFF"))
            .setBackgroundTint(Color.parseColor("#FECE00"))
            .setTextColor(Color.parseColor("#FFFFFF"))

        val params = mSnackBar.view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        mSnackBar.view.layoutParams = params
        mSnackBar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        mSnackBar.show()
    }
    fun openDialog() {
        var dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_logout_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        var tvDialogCancel : TextView = dialog.findViewById(R.id.tvDialogCancel)
        var tvDialogLogout : TextView = dialog.findViewById(R.id.tvDialogLogout)
        tvDialogCancel.setOnClickListener {
            dialog.dismiss()
        }
        tvDialogLogout.setOnClickListener {
            Constant.account = Account("", "", "", "", 0)

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        dialog.show()
    }

}