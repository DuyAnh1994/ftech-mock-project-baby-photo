package ai.ftech.babyphoto.screen.album


import ai.ftech.babyphoto.model.IPhotoFolder
import ai.ftech.babyphoto.screen.adapter.PhotoFolderAdapter
import android.Manifest
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager

class PhotoFolderPresenter(activity: PhotoFolderActivity) : IPhotoFolder {
    private val view = activity

    override fun setImage() {
        // xin quyền truy cập trong máy ảnh
        if (ContextCompat.checkSelfPermission(view, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                view,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 999
            )
        }

        val arrayImage: MutableList<String> = ArrayList()
        var projection = arrayOf<String>(
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
            cursor.close();
        }
        val gridLayoutManager = GridLayoutManager(view, 3)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        view.rvPhotoFolderImage.layoutManager = gridLayoutManager
        val adapter = PhotoFolderAdapter(view, arrayImage)
        view.rvPhotoFolderImage.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun backCreateAlbum() {
        view.ivCancel.setOnClickListener {
            view.openBackDialog()
        }
    }

}

