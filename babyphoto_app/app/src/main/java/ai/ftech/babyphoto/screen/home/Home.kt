package ai.ftech.babyphoto.screen.home

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.Constant
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.model.AlbumBaby
import ai.ftech.babyphoto.model.Image
import ai.ftech.babyphoto.model.ResponseModel
import ai.ftech.babyphoto.screen.detailaccount.DetailAccount
import ai.ftech.babyphoto.screen.timeline.Timeline
import ai.ftech.babyphoto.screen.timeline.TimelinePresenter
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_timeline.*
import kotlinx.android.synthetic.main.home_view_baby_layout.*
import kotlinx.android.synthetic.main.nv_home_header_layout.*
import kotlinx.android.synthetic.main.nv_home_header_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : AppCompatActivity(), BabyHomeAdapter.onItemClickListenerr, IHomeContract.View {
    @SuppressLint("WrongViewCast", "NotifyDataSetChanged")
    private lateinit var presenter: HomePresenter
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    private var mutableListBaby: MutableList<AlbumBaby> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        presenter = HomePresenter(this)
        drawerLayout = findViewById(R.id.drawableLayout)
        navigationView = findViewById(R.id.nvHomeToDetailAccount)

        rcvHomeViewBaby.layoutManager = LinearLayoutManager(this)

        val adapter =
            BabyHomeAdapter(this@Home, mutableListBaby, R.drawable.ic_add_home_24px)
        adapter.setOnItemClickListener(this)
        rcvHomeViewBaby.adapter = adapter
        val manager = GridLayoutManager(this@Home, 2, GridLayoutManager.VERTICAL, false)
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

        nvHomeToDetailAccount.getHeaderView(0).tvHeaderNameAccount.text = Constant.account.firstname + " " +
                Constant.account.lastname

        ibHomeMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        navigationView.setNavigationItemSelectedListener {
            var intent = Intent(this, DetailAccount::class.java)
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
        var arrayImage = mutableListOf(R.drawable.ad_home11, R.drawable.ad_home12,
            R.drawable.img_ads_home1, R.drawable.image_ads_home2)
        for( image in arrayImage){
            flipperImage(image)
        }
        ibCancelAds.setOnClickListener {
            vlHomeSlide.removeAllViews()
            ibCancelAds.visibility = View.INVISIBLE
        }


    }
    private fun flipperImage(image: Int) {
        val ivHomeSlide = ImageView(this)
        ivHomeSlide.scaleType = ImageView.ScaleType.CENTER_CROP
        ivHomeSlide.adjustViewBounds = true
        ivHomeSlide.setBackgroundResource(image)
        vlHomeSlide.addView(ivHomeSlide)
        vlHomeSlide.flipInterval = 5000
        vlHomeSlide.setInAnimation(this, R.anim.slide_left)
//        vlHomeSlide.setOutAnimation(this, R.anim.slide_right)
        vlHomeSlide.isAutoStart = true
//        vlHomeSlide.setInAnimation(in)

    }

    override fun onItemClick(position: Int) {
        var intent = Intent(this, Timeline::class.java)
        intent.putExtra("idalbum", mutableListBaby[position].idalbum)
        intent.putExtra("nameAlbum", mutableListBaby[position].name)
        intent.putExtra("urlimage", mutableListBaby[position].urlimage)
        intent.putExtra("birthday", mutableListBaby[position].birthday)
        startActivity(intent)
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