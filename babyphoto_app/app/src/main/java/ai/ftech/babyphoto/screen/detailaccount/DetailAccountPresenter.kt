package ai.ftech.babyphoto.screen.detailaccount

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.android.synthetic.main.activity_detail_account.*


class DetailAccountPresenter(activity: DetailAccount) {
    private val view = activity

    @SuppressLint("ServiceCast", "RestrictedApi")
    fun coppyClipboardManager() {
        val stringYouExtracted: String = view.tvViewIDAccountDetail.getText().toString()
        val clipboard =
            getActivity(view)!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", stringYouExtracted)

        clipboard.setPrimaryClip(clip)
        Toast.makeText(
            getActivity(view),
            "Copy coupon code copied to clickboard!",
            Toast.LENGTH_SHORT
        )
            .show()
    }
}