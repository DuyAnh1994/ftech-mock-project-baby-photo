package ai.ftech.babyphoto.screen.listimage

import ai.ftech.babyphoto.R
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayOutputStream
import java.util.*

class ListImageActivity : AppCompatActivity() {
    lateinit var rvImageView: RecyclerView
    lateinit var ivCancel: ImageView
    lateinit var btnAdd: Button
    lateinit var tvTitle: TextView
    private lateinit var listImagePresent: ListImagePresenter
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_image_activity)
        initView()
        default()
        listImagePresent.checkPermission()
        listImagePresent.cancelCreate()
    }


    private fun initView() {
        rvImageView = findViewById(R.id.rvListImage)
        ivCancel = findViewById(R.id.ivListImageCancel)
        btnAdd = findViewById(R.id.btnListImageAdd)
        tvTitle = findViewById(R.id.tvListImageTitle)
        listImagePresent = ListImagePresenter(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun default() {
        // nhận kết quả trả về từ máy ảnh
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result?.resultCode == RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    val bitmap: Bitmap = intent.extras?.get("data") as Bitmap
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 5, baos)
                    val b: ByteArray = baos.toByteArray()
                    val base64Avatar = Base64.getEncoder().encodeToString(b)
                    listImagePresent.postServer(base64Avatar)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            998 -> {
                listImagePresent.getImage()
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