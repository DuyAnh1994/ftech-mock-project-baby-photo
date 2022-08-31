package ai.ftech.babyphoto.screen.home

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.data.Constant
import ai.ftech.babyphoto.data.model.AlbumBaby
import ai.ftech.babyphoto.screen.detailaccount.DetailAccountActivity
import ai.ftech.babyphoto.screen.timeline.TimelineActivity
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nv_home_header_layout.view.*

class HomeActivity : AppCompatActivity(), BabyHomeAdapter.onItemClickListenerr, IHomeContract.View {
    @SuppressLint("WrongViewCast", "NotifyDataSetChanged")
    private lateinit var presenter: HomePresenter
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    private var mutableListBaby: MutableList<AlbumBaby> = mutableListOf()

    //callback result
    private var getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            mutableListBaby.clear()
            srlHome.isRefreshing = true
            presenter.getAlbum(Constant.account.idaccount)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        presenter = HomePresenter(this)
        drawerLayout = findViewById(R.id.drawableLayout)
        navigationView = findViewById(R.id.nvHomeToDetailAccount)

        rcvHomeViewBaby.layoutManager = LinearLayoutManager(this)

        val adapter =
            BabyHomeAdapter(this@HomeActivity, mutableListBaby, R.drawable.ic_add_home_24px)
        adapter.setOnItemClickListener(this)
        rcvHomeViewBaby.adapter = adapter
        val manager = GridLayoutManager(this@HomeActivity, 2, GridLayoutManager.VERTICAL, false)
        rcvHomeViewBaby.layoutManager = manager


        srlHome.isRefreshing = true
        presenter.getAlbum(Constant.account.idaccount)

        srlHome.setOnRefreshListener {
            mutableListBaby.clear()
            srlHome.isRefreshing = true
            presenter.getAlbum(Constant.account.idaccount)
        }


        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //view info in nav
        nvHomeToDetailAccount.getHeaderView(0).tvHeaderNameAccount.text = Constant.account.firstname + " " +
                Constant.account.lastname

        ibHomeMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        navigationView.setNavigationItemSelectedListener {
            var intent = Intent(this, DetailAccountActivity::class.java)
            when (it.itemId) {
                R.id.itemAcc -> startActivity(intent)
                R.id.itemNoti -> Toast.makeText(
                    applicationContext,
                    "Click on Notifications",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.itemSto -> Toast.makeText(
                    applicationContext,
                    "Click on Storate",
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
        }


        //ads
        var arrayImage = mutableListOf(
            R.drawable.image16, R.drawable.image17,
            R.drawable.image13, R.drawable.image14, R.drawable.image15
        )

        for (image in arrayImage) {
            flipperImage(image)
        }
        ibCancelAds.setOnClickListener {
//            vlHomeSlide.removeAllViews()
//            ibCancelAds.visibility = View.INVISIBLE
        }

    }

    private fun flipperImage(image: Int) {
        val ivHomeSlide = ImageView(this)
        ivHomeSlide.scaleType = ImageView.ScaleType.CENTER_CROP
        ivHomeSlide.adjustViewBounds = true
        ivHomeSlide.setBackgroundResource(image)
        vlHomeSlide.addView(ivHomeSlide)
        vlHomeSlide.flipInterval = 5000
        vlHomeSlide.setInAnimation(this, R.anim.slide_right)
        vlHomeSlide.setOutAnimation(this, R.anim.slide_left)
//        vlHomeSlide.setOutAnimation(this, R.anim.slide_right)
        vlHomeSlide.isAutoStart = true
//        vlHomeSlide.setInAnimation(in)

    }

    override fun onItemClick(position: Int) {
        var intent = Intent(this, TimelineActivity::class.java)
        intent.putExtra("idalbum", mutableListBaby[position].idalbum)
        intent.putExtra("nameAlbum", mutableListBaby[position].name)
        intent.putExtra("urlimage", mutableListBaby[position].urlimage)
        intent.putExtra("birthday", mutableListBaby[position].birthday)
//        startActivity(intent)
        getResult.launch(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onGetAlbum(state: HomeState, message: String, lAbum: List<AlbumBaby>) {
        srlHome.isRefreshing = false
        when (state) {
            HomeState.SUCCESS -> {
                this.mutableListBaby.addAll(lAbum)
                rcvHomeViewBaby.adapter!!.notifyDataSetChanged()
            }
            HomeState.GET_ALBUM_FAIL -> {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }


}