package ai.ftech.babyphoto.screen.detailaccount


import ai.ftech.babyphoto.MainActivity
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.model.AccountUpdate
import ai.ftech.babyphoto.model.AlbumBaby
import ai.ftech.babyphoto.model.ResponseModel
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

    //    private var mutableListAccount: MutableList<AccountUpdate> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ai.ftech.babyphoto.R.layout.activity_detail_account)
        val bundle: Bundle? = intent.extras
        val idaccount = bundle?.get("idaccount")
//        var idaccount = 2
        presenter = DetailAccountPresenter(this)
        tvCoppyIDAccount.setOnClickListener {
            presenter!!.coppyClipboardManager()
        }
        ibAccountDetailBack.setOnClickListener {
            finish()
        }
        APIService().base().account().enqueue(
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
                        tvViewIDAccountDetail.text = idaccount.toString()
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
//        edtAccountDetailName.text.toString()
//edtViewEmailAccountDetail.text
//        edtAccountDetailNameLast.text
//        tvViewIDAccountDetail.text
//        edtViewEmailAccountDetail.text

       llAccountDetailSaveChange.setOnClickListener {

           APIService().base().updateAccount(
               edtViewEmailAccountDetail.text.toString(), edtAccountDetailName.text.toString(), edtAccountDetailNameLast.text.toString(),
               idaccount as Int
           ).enqueue(
               object : Callback<String> {
                   override fun onResponse(call: Call<String>, response: Response<String>) {
//                       tvViewIDAccountDetail.text = idaccount.toString()
//                       edtViewEmailAccountDetail.setText(edtViewEmailAccountDetail.text.toString())
//                       edt
                       Log.d("TAG", "onResponse: Update Success")

//                       Log.d("TAG", "onResponse: ${response.body()}")
                   }
                   override fun onFailure(call: Call<String>, t: Throwable) {
                       Log.d("TAG", "ERROR: Update Fail")
                   }
               })
       }
        llAccountDetailLogout.setOnClickListener{
            var intent = Intent(this@DetailAccount, MainActivity::class.java)
            startActivity(intent)
        }
//        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this)
//            .setSmallIcon(ai.ftech.babyphoto.R.drawable.ic_account_detail_edit_24px)
//            .setContentTitle("hi")
//            .setContentText("the answer is corect")
//            .setContentIntent(this)
//            .setTicker("Your message")
//
//        val params = WindowManager.LayoutParams(
//            WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
//            WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, PixelFormat.TRANSLUCENT
//        )
//
//        params.gravity = Gravity.CENTER or Gravity.TOP
//        view.getSystemService(Context.WINDOW_SERVICE).addView(this, params)
//    }
        Snackbar.make(
            findViewById(ai.ftech.babyphoto.R.id.llAccountDetailLogout),
            ai.ftech.babyphoto.R.string.add_baby,
            Snackbar.LENGTH_SHORT
        ).show()
//        val mySnackbar = Snackbar.make(this, "stringId", "duration")
//        mySnackbar.show()
    }
}