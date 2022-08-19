package ai.ftech.babyphoto.screen.createalbum.preview

import ai.ftech.babyphoto.R
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


class PhotoFolderActivity : AppCompatActivity(), DialogPreviewFragment.IPreviewUri {
    lateinit var ivCancel: ImageView
    lateinit var ivCamera: ImageView
    lateinit var rvPhotoFolderImage: RecyclerView
    var uriImage: String? = null
    private lateinit var photoFolderPresenter: PhotoFolderPresenter
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_folder_activity)
        initView()
        default()
        photoFolderPresenter = PhotoFolderPresenter(this)
        photoFolderPresenter.checkPermission()
        photoFolderPresenter.backCreateAlbum()
        photoFolderPresenter.setCamera()

    }

    private fun default() {
        // nhận kết quả trả về từ máy ảnh
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result?.resultCode == RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    val bitmap  = intent.extras?.get("data") as Bitmap
                    val intent1 = Intent()
                    intent1.putExtra("uriImage", bitmap)
                    setResult(123, intent1)
                    finish()
                }
            }
        }
    }


    private fun initView() {
        ivCancel = findViewById(R.id.ivPhotoFolderCancel)
        ivCamera = findViewById(R.id.ivPhotoFolderCamera)
        rvPhotoFolderImage = findViewById(R.id.rvPhotoFolderImage)
    }


    override fun getBitmap(pathImage : String) {
        uriImage = pathImage
        val intent = Intent()
        intent.putExtra("uri", uriImage)
        setResult(234, intent)
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            998 -> {
                photoFolderPresenter.getImage()
            }
            999 -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    activityResultLauncher.launch(intent)
                }
                return
            }

            else -> {

            }
        }
    }

}