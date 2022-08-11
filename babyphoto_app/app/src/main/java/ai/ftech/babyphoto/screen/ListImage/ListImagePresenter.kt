package ai.ftech.babyphoto.screen.ListImage

import ai.ftech.babyphoto.screen.adapter.ListImageAdapter
import android.Manifest
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager


class ListImagePresenter(activity: ListImageActivity) {
    private val view = activity

    fun checkPermission() {
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

    fun getImage() {
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
        view.rvImageView.layoutManager = gridLayoutManager
        val adapter = ListImageAdapter(arrayImage)
        view.rvImageView.adapter = adapter

    }

}