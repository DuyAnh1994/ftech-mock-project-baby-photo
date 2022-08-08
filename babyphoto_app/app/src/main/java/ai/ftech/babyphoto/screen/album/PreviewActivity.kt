package ai.ftech.babyphoto.screen.album

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.screen.album.PreviewPresenter
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.widget.Button
import android.widget.ImageView

class PreviewActivity : AppCompatActivity() {
    lateinit var ivCancel: ImageView
    lateinit var ivOk: ImageView
    lateinit var ivBaby: ImageView
    lateinit var previewPresenter: PreviewPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preview_activity)
        initView()

        val intent = intent
        var uriBaby : String = intent.getStringExtra("uriImage")!!
        previewPresenter.setCancel()
        if(uriBaby != null) {
            previewPresenter.setInsert(uriBaby)
        }
        previewPresenter.setIntent(uriBaby)
    }

    private fun initView() {
        ivCancel = findViewById(R.id.ivPreviewCancel)
        ivOk = findViewById(R.id.ivPreviewOk)
        ivBaby = findViewById(R.id.ivPreviewBaby)
        previewPresenter = PreviewPresenter(this)
    }

    fun openBackDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_back_create_album_layout)
        val window: Window = dialog.window ?: return
        if (window == null) {
            return
        }
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        val windowAttributes = window.attributes
        windowAttributes.gravity = Gravity.CENTER
        window.attributes = windowAttributes
        val btnCancel: Button = dialog.findViewById(R.id.btnDialogBacKCancel)
        val btnOK: Button = dialog.findViewById(R.id.btnDialogBacKOk)
        btnOK.setOnClickListener {
            finish()
            dialog.dismiss()
        }
        btnCancel.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }


}