package ai.ftech.babyphoto.screen.detailaccount


import android.content.Context
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail_account.*


class DetailAccount : AppCompatActivity() {
    private var presenter: DetailAccountPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ai.ftech.babyphoto.R.layout.activity_detail_account)
        presenter = DetailAccountPresenter(this)
        tvCoppyIDAccount.setOnClickListener {
            presenter!!.coppyClipboardManager()
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