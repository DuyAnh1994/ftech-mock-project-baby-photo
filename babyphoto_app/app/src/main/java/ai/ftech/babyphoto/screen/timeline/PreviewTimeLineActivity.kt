package ai.ftech.babyphoto.screen.timeline

import ai.ftech.babyphoto.R
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_preview_time_line.*
import kotlinx.android.synthetic.main.activity_timeline.*

class PreviewTimeLineActivity : AppCompatActivity() {
    private var imagePre: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_time_line)
        var bundle: Bundle? = intent.extras
        imagePre = bundle?.getString("image").toString()
        Glide.with(this)
            .load(imagePre)
            .placeholder(R.drawable.image_default)
            .into(ivPreviewTimeLine)
        ibPreviewBack.setOnClickListener {
            finish()
        }
    }
}