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

//        APIService.base().getAlbumId(idaccount as Int).enqueue(
//            object : Callback<ResponseModel<List<AlbumBaby>>> {
//                override fun onResponse(
//                    call: Call<ResponseModel<List<AlbumBaby>>>,
//                    response: Response<ResponseModel<List<AlbumBaby>>>
//                ) {
//                    res = response.body()!!
//                    mutableListBaby1.addAll(res.data)
//                    srlHome.setOnRefreshListener {
//                        mutableListBaby1.clear()
//                        mutableListBaby1.addAll(res.data)
//                        recycleBaby.adapter!!.notifyDataSetChanged()
//                        srlHome.isRefreshing = false
//                    }
//                    var adapter =
//                        BabyHomeAdapter(this@Home, mutableListBaby1, R.drawable.ic_add_home_24px)
//                    recycleBaby.adapter = adapter
//                    adapter.setOnItemClickListener(this@Home)
//
////                    val manager = GridLayoutManager(this@Home, 2, GridLayoutManager.VERTICAL, false)
////                    recycleBaby.layoutManager = manager
//                }
//
//                override fun onFailure(call: Call<ResponseModel<List<AlbumBaby>>>, t: Throwable) {
//                    Toast.makeText(this@Home, "Get album failed", Toast.LENGTH_SHORT).show()
//                }
//
//            }
//        )


        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
        when(state){
            HomeState.SUCCESS ->{
                this.mutableListBaby.addAll(lAbum)
                rcvHomeViewBaby.adapter!!.notifyDataSetChanged()
            }
            HomeState.GET_ALBUM_FAIL ->{
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
            else ->{}
        }
    }


}