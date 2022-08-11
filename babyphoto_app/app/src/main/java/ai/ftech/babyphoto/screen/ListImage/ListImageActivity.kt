package ai.ftech.babyphoto.screen.ListImage

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.screen.adapter.ListImageAdapter
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListImageActivity : AppCompatActivity() {
    lateinit var rvImageView: RecyclerView
    lateinit var ivCancel : ImageView
    lateinit var btnAdd : Button
    lateinit var listImagePresent : ListImagePresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_image_activity)
        initView()
        listImagePresent.checkPermission()
    }



    private fun initView() {
        rvImageView = findViewById(R.id.rvListImage)
        ivCancel = findViewById(R.id.ivListImageCancel)
        btnAdd = findViewById(R.id.btnListImageAdd)
        listImagePresent = ListImagePresenter(this)
    }

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
//            999 -> {
//                // If request is cancelled, the result arrays are empty.
//                if ((grantResults.isNotEmpty() &&
//                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                ) {
//                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    activityResultLauncher.launch(intent)
//                }
//                return
//            }

            else -> {

            }
        }
    }


}