package ai.ftech.babyphoto

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //khai báo presenter
    private var presenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //khai báo biến presenter
        presenter = MainPresenter(this)

        //gọi hàm lấy tài khoản
        presenter!!.getAccount()

        btnInsert.setOnClickListener { _ -> presenter!!.insertAccount() }
    }
}