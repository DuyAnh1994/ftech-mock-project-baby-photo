package ai.ftech.babyphoto.screen.album

import ai.ftech.babyphoto.R
import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class PhotoFolderActivity : AppCompatActivity() {
    lateinit var ivCancel: ImageView
    lateinit var ivCamera: ImageView
    lateinit var rvPhotoFolderImage: RecyclerView

    private lateinit var photoFolderPresenter: PhotoFolderPresenter
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_folder_activity)
        initView()
        default()

        photoFolderPresenter = PhotoFolderPresenter(this)
        photoFolderPresenter.setImage()
        photoFolderPresenter.backCreateAlbum()
        setCamera()

    }

    private fun default() {
        // nhận kết quả trả về từ máy ảnh
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result?.resultCode == RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    val bitmap : Bitmap = intent.extras?.get("data") as Bitmap
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

    private fun setCamera() {
        ivCamera.setOnClickListener {
            //check quyền camera
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(Manifest.permission.CAMERA), 999
                )
            }
            // chuyển sang máy ảnh
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            activityResultLauncher.launch(intent)
        }

    }

    fun openBackDialog() {
        val dialog = Dialog(this)
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
            val intent = Intent(this, CreateAlbumActivity::class.java)
            startActivity(intent)
            finish()
            dialog.dismiss()
        }
        btnCancel.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }
}