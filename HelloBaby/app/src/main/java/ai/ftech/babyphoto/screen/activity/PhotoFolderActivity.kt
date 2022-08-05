package ai.ftech.babyphoto.screen.activity

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.screen.presenter.PhotoFolderPresenter
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


class PhotoFolderActivity : AppCompatActivity() {
    lateinit var ivCancel: ImageView
    lateinit var ivCamera: ImageView
    lateinit var rvPhotoFolderImage: RecyclerView
    private lateinit var photoFolderPresenter: PhotoFolderPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_folder_activity)
        initView()
        photoFolderPresenter = PhotoFolderPresenter(this)
        photoFolderPresenter.setImage()
        photoFolderPresenter.backCreateAlbum()
    }


    private fun initView() {
        ivCancel = findViewById(R.id.ivPhotoFolderCancel)
        ivCamera = findViewById(R.id.ivPhotoFolderCamera)
        rvPhotoFolderImage = findViewById(R.id.rvPhotoFolderImage)
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
            val intent = Intent(this, CreateAlbumActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
        btnCancel.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

}