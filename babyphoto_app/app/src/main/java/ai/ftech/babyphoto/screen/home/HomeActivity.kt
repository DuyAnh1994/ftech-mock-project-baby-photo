package ai.ftech.babyphoto.screen.home

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.screen.createalbum.CreateAlbumActivity
import ai.ftech.babyphoto.screen.listimage.ListImageActivity
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val flAddBaby: FrameLayout = findViewById(R.id.flHomeAddBaby)
        val flAlbum: FrameLayout = findViewById(R.id.flHomeJoinAlbum)
        flAddBaby.setOnClickListener {
            val intent = Intent(this, CreateAlbumActivity::class.java)
            startActivity(intent)
        }
        flAlbum.setOnClickListener {
            val intent = Intent(this, ListImageActivity::class.java)
            startActivity(intent)
        }
    }
}