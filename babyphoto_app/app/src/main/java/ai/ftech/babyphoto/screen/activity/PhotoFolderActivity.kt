package ai.ftech.babyphoto.screen.activity

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.screen.adapter.PhotoFolderAdapter
import ai.ftech.babyphoto.model.IPhotoFolder
import ai.ftech.babyphoto.screen.presenter.PhotoFolderPresenter
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PhotoFolderActivity : AppCompatActivity(){
     lateinit var ivCancel: ImageView
     lateinit var ivCamera: ImageView
     lateinit var rvPhotoFolderImage: RecyclerView
    lateinit var photoFolderPresenter: PhotoFolderPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_folder_activity)
        initView()
        photoFolderPresenter = PhotoFolderPresenter(this)
        photoFolderPresenter.setImage()
    }


    private fun initView() {
        ivCancel = findViewById(R.id.ivPhotoFolderCancel)
        ivCamera = findViewById(R.id.ivPhotoFolderCamera)
        rvPhotoFolderImage = findViewById(R.id.rvPhotoFolderImage)
    }

}