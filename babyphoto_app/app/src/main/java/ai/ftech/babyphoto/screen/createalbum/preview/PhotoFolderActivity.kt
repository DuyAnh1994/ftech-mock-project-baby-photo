package ai.ftech.babyphoto.screen.createalbum.preview

import ai.ftech.babyphoto.R
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PhotoFolderActivity : AppCompatActivity(), DialogPreviewFragment.IPreviewUri,
    IPhotoContract.IView {
    lateinit var ivCancel: ImageView
    lateinit var ivCamera: ImageView
    lateinit var rvPhotoFolderImage: RecyclerView
    val REQUEST_CODE_CAMERA = 999
    val REQUEST_CODE_IMAGE = 998
    val RESULT_CODE_CAMERA = 123
    val RESULT_CODE_IMAGE = 234
    var uriImage: String? = null
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var photoFolderPresenter: PhotoFolderPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_folder_activity)
        initView()
        default()
        checkPermission()
        backCreateAlbum()
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
                    val bitmap = intent.extras?.get("data") as Bitmap
                    val intent1 = Intent()
                    intent1.putExtra("uriImage", bitmap)
                    setResult(RESULT_CODE_CAMERA, intent1)
                    finish()
                }
            }
        }
    }


    private fun initView() {
        ivCancel = findViewById(R.id.ivPhotoFolderCancel)
        ivCamera = findViewById(R.id.ivPhotoFolderCamera)
        rvPhotoFolderImage = findViewById(R.id.rvPhotoFolderImage)
        photoFolderPresenter = PhotoFolderPresenter(this)
    }


    override fun getBitmap(pathImage: String) {
        uriImage = pathImage
        val intent = Intent()
        intent.putExtra("uri", uriImage)
        setResult(RESULT_CODE_IMAGE, intent)
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_IMAGE -> {
                photoFolderPresenter?.getImage(this)
            }
            REQUEST_CODE_CAMERA -> {
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

    fun checkPermission() {
        // xin quyền truy cập trong thư viện
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_IMAGE
            )
        }
        photoFolderPresenter?.getImage(this)
    }


    override fun data(arrayImage: MutableList<String>) {
        val gridLayoutManager = GridLayoutManager(this, 3)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        rvPhotoFolderImage.layoutManager = gridLayoutManager
        val adapter = PhotoFolderAdapter(this, arrayImage, object : IPreview {
            override fun setInsert(uriBaby: String) {
                val dialogPreviewFragment = DialogPreviewFragment()
                val bundle = Bundle()
                bundle.putString("urlImage", uriBaby)
                bundle.putBoolean("status",true)
                dialogPreviewFragment.arguments = bundle
                dialogPreviewFragment.show(supportFragmentManager, dialogPreviewFragment.tag)
            }

        })
        rvPhotoFolderImage.adapter = adapter
    }

    fun setCamera() {
        ivCamera.setOnClickListener {
            //check quyền camera

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_CAMERA
                )
            }
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            activityResultLauncher.launch(intent)
        }

    }

    fun backCreateAlbum() {
        ivCancel.setOnClickListener {
            photoFolderPresenter?.openBackDialog(this)
        }
    }


}