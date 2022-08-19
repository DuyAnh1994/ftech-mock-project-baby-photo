package ai.ftech.babyphoto.screen.detailaccount

import ai.ftech.babyphoto.MainActivity
import ai.ftech.babyphoto.R
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail_account.*


class DetailAccountPresenter(activity: DetailAccount) {
    private val view = activity


    @SuppressLint("ServiceCast", "RestrictedApi")
    fun coppyClipboardManager() {
        val stringYouExtracted: String = view.tvViewIDAccountDetail.text.toString()
        val clipboard =
            getActivity(view)!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", stringYouExtracted)

        clipboard.setPrimaryClip(clip)
        showSnackbar("ID copied")
        Toast.makeText(
            getActivity(view),
            "Copy coupon code copied to clickboard!",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    fun showSnackbar(content: String) {
        val mSnackBar = Snackbar.make(view.detailAcccountMain, content, Snackbar.LENGTH_LONG)
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
        var dialog = Dialog(view)
        dialog.setContentView(R.layout.dialog_logout_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        var tvDialogCancel : TextView = dialog.findViewById(R.id.tvDialogCancel)
        var tvDialogLogout : TextView = dialog.findViewById(R.id.tvDialogLogout)
        tvDialogCancel.setOnClickListener {
            dialog.dismiss()
        }
        tvDialogLogout.setOnClickListener {
            var intent = Intent(view, MainActivity::class.java)
            view.startActivity(intent)
        }
        dialog.show()
    }
}