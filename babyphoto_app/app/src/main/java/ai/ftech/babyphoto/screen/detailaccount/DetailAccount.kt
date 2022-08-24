package ai.ftech.babyphoto.screen.detailaccount


import ai.ftech.babyphoto.MainActivity
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.model.AccountUpdate
import ai.ftech.babyphoto.model.AlbumBaby
import ai.ftech.babyphoto.model.ResponseModel
import ai.ftech.babyphoto.screen.changepass.ChangePass
import ai.ftech.babyphoto.screen.home.BabyHomeAdapter
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail_account.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailAccount : AppCompatActivity() {
    private var presenter: DetailAccountPresenter? = null
    private var lAccount = mutableListOf<Account>()
    private var email: String = ""
    private var firstname: String = ""
    private var lastname: String = ""
    private var index1: Int = 0
    private var idaccount1: Int = 0

    //    private var mutableListAccount: MutableList<AccountUpdate> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ai.ftech.babyphoto.R.layout.activity_detail_account)
        val bundle: Bundle? = intent.extras
        var idaccount = bundle?.get("idaccount")
        val showTop = bundle?.get("showTop")
        presenter = DetailAccountPresenter(this)
        tvCoppyIDAccount.setOnClickListener {
            presenter!!.coppyClipboardManager()
        }
        ibAccountDetailBack.setOnClickListener {
            finish()
        }
        APIService.base().account().enqueue(
            object : Callback<ResponseModel<List<Account>>> {
                override fun onResponse(
                    call: Call<ResponseModel<List<Account>>>,
                    response: Response<ResponseModel<List<Account>>>
                ) {
                    if (response.body() != null) {
                        response.body()!!.data.also { lAccount = it as MutableList<Account> }
                        lAccount.forEachIndexed { index, account ->
                            if (account.idaccount == idaccount)
                                index1 = index
                        }
                        email = lAccount[index1].email
                        firstname = lAccount[index1].firstname
                        lastname = lAccount[index1].lastname
                        idaccount1 = lAccount[index1].idaccount
                        tvViewIDAccountDetail.text = idaccount1.toString()
                        edtAccountDetailName.setText(firstname)
                        edtAccountDetailNameLast.setText(lastname)
                        edtViewEmailAccountDetail.setText(email)
//                        Toast.makeText(view, "Get data success", Toast.LENGTH_SHORT).show()
                        return
                    }
                    Toast.makeText(this@DetailAccount, "Data is empty", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<ResponseModel<List<Account>>>, t: Throwable) {
                    Toast.makeText(this@DetailAccount, "Get data failed", Toast.LENGTH_SHORT).show()
                    Log.e("ERROR", t.toString())
                }

            }
        )
       llAccountDetailSaveChange.setOnClickListener {

          APIService.base().updateAccount(
               edtViewEmailAccountDetail.text.toString(), edtAccountDetailName.text.toString(), edtAccountDetailNameLast.text.toString(),
               idaccount as Int
           ).enqueue(
               object : Callback<String> {
                   override fun onResponse(call: Call<String>, response: Response<String>) {
                       presenter!!.showSnackbar("Change Account Success!")
                       Log.d("TAG", "onResponse: Update Success")

                   }
                   override fun onFailure(call: Call<String>, t: Throwable) {
                       presenter!!.showSnackbar("Change Account Success!")
                       Log.d("TAG", "ERROR: Update Fail")
                   }
               })
       }
        llAccountDetailLogout.setOnClickListener{
            presenter!!.openDialog()
        }
        clProfileChangePass.setOnClickListener {
            var intent = Intent(this@DetailAccount, ChangePass::class.java)
            intent.putExtra("idaccount", idaccount as Int)
            startActivity(intent)
        }
        Snackbar.make(
            findViewById(ai.ftech.babyphoto.R.id.llAccountDetailLogout),
            ai.ftech.babyphoto.R.string.add_baby,
            Snackbar.LENGTH_SHORT
        ).show()

    }
}