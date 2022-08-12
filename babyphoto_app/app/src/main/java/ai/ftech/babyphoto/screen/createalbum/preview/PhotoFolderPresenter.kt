package ai.ftech.babyphoto.screen.createalbum.preview


import ai.ftech.babyphoto.R
import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.Window
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager


class PhotoFolderPresenter(activity: PhotoFolderActivity) : IPhotoFolder {
    private val view = activity

    override fun checkPermission() {
        // xin quyền truy cập trong máy ảnh
        if (ContextCompat.checkSelfPermission(view, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                view,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 998
            )
        }
        getImage()
    }

    fun getImage(){
        val arrayImage: MutableList<String> = ArrayList()
        var projection = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATA,
        )

        val cursor = view.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection, null, null, null
        )
        if (cursor!!.count > 0) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast()) {
                val data: String =
                    cursor.getString(2);
                arrayImage.add(data);
                cursor.moveToNext();
            }
            cursor.close()
        }

        val gridLayoutManager = GridLayoutManager(view, 3)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        view.rvPhotoFolderImage.layoutManager = gridLayoutManager
        val adapter = PhotoFolderAdapter(view, arrayImage, object : IPreview {
            override fun setInsert(uriBaby: String) {
                val dialogPreviewFragment = DialogPreviewFragment()
                val bundle = Bundle()
                bundle.putString("urlImage", uriBaby)
                dialogPreviewFragment.setArguments(bundle)
                dialogPreviewFragment.show(view.supportFragmentManager, dialogPreviewFragment.tag)
            }

        })
        view.rvPhotoFolderImage.adapter = adapter
    }

    fun setCamera() {
        view.ivCamera.setOnClickListener {
            //check quyền camera
            if (ContextCompat.checkSelfPermission(view, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    view,
                    arrayOf(Manifest.permission.CAMERA), 999
                )
            }
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            view.activityResultLauncher.launch(intent)
        }

    }

    override fun backCreateAlbum() {
        view.ivCancel.setOnClickListener {
            openBackDialog()
        }
    }

    fun openBackDialog() {
        val dialog = Dialog(view)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_back_create_album_layout)
        val window: Window = dialog.window ?: return
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        val windowAttributes = window.attributes
        windowAttributes.gravity = Gravity.CENTER
        window.attributes = windowAttributes
        val btnCancel: Button = dialog.findViewById(R.id.btnDialogBacKCancel)
        val btnOK: Button = dialog.findViewById(R.id.btnDialogBacKOk)
        btnOK.setOnClickListener {
            view.finish()
            dialog.dismiss()
        }
        btnCancel.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

}

