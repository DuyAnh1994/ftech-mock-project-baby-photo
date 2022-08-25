package ai.ftech.babyphoto.screen.test

import ai.ftech.babyphoto.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        btnBackTest.setOnClickListener {
            finish()
        }
    }
}