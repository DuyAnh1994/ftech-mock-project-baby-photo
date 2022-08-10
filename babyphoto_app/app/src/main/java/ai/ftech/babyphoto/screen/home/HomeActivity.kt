package ai.ftech.babyphoto.screen.home

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.screen.album.CreateAlbumActivity
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ai.ftech.babyphoto.R.layout.home_activity)
        val flAddBaby : FrameLayout = findViewById(R.id.flHomeAddBaby)

        flAddBaby.setOnClickListener {
            val intent = Intent(this,CreateAlbumActivity:: class.java)
            startActivity(intent)
        }
    }
}