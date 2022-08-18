package ai.ftech.babyphoto.screen.home

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.model.AlbumBaby
import ai.ftech.babyphoto.model.Image
import ai.ftech.babyphoto.model.ResponseModel
import ai.ftech.babyphoto.screen.detailaccount.DetailAccount
import ai.ftech.babyphoto.screen.timeline.Timeline
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.home_view_baby_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : AppCompatActivity(), BabyHomeAdapter.onItemClickListenerr {
    @SuppressLint("WrongViewCast", "NotifyDataSetChanged")
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView

    private var mutableListBaby: MutableList<AlbumBaby> = mutableListOf()
    private val mutableListBaby1: MutableList<AlbumBaby> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        drawerLayout = findViewById(R.id.drawableLayout)
        navigationView = findViewById(R.id.nvHomeToDetailAccount)
        val bundle: Bundle? = intent.extras
        val idaccount = bundle?.get("idaccount")
        val recycleBaby: RecyclerView = findViewById(R.id.rcvHomeViewBaby)
        recycleBaby.layoutManager = LinearLayoutManager(this)

//        mutableListBaby.add(AlbumBaby("", "nomo1", "4", "items", "", "", "", ""))
//
        var adapter =
            BabyHomeAdapter(this@Home, mutableListBaby, "Add Baby")
        recycleBaby.adapter = adapter
//        adapter.setOnItemClickListener(object : BabyHomeAdapter.onItemClickListener{
//            override fun onItemClick(position: Int) {
//                Toast.makeText(this@Home, "You Click on$position", Toast.LENGTH_SHORT).show()
//            }
//
//        })
        val manager = GridLayoutManager(this@Home, 2, GridLayoutManager.VERTICAL, false)
        recycleBaby.layoutManager = manager

//        val idaccount = bundle.get("idaccount")
//        APIService().base().getAlbumId(idaccount as Int).enqueue(
        APIService().base().getAlbumId(idaccount as Int).enqueue(
            object : Callback<ResponseModel<List<AlbumBaby>>> {
                override fun onResponse(
                    call: Call<ResponseModel<List<AlbumBaby>>>,
                    response: Response<ResponseModel<List<AlbumBaby>>>
                ) {
                    val res = response.body() as ResponseModel<List<AlbumBaby>>
//                    print(res.data)
                    mutableListBaby1.addAll(res.data)
                    var adapter =
                        BabyHomeAdapter(this@Home, mutableListBaby1, "Add Baby")
                    recycleBaby.adapter = adapter
                    adapter.setOnItemClickListener(this@Home)
//                    val manager = GridLayoutManager(this@Home, 2, GridLayoutManager.VERTICAL, false)
//                    recycleBaby.layoutManager = manager
                }

                override fun onFailure(call: Call<ResponseModel<List<AlbumBaby>>>, t: Throwable) {
                    Toast.makeText(this@Home, "Get album failed", Toast.LENGTH_SHORT).show()
                }

            }
        )

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationView.setNavigationItemSelectedListener {
            var intent = Intent(this, DetailAccount::class.java)
            intent.putExtra("idaccount", idaccount)
            when(it.itemId){
                R.id.itemAcc-> startActivity(intent)
                R.id.itemNoti-> Toast.makeText(applicationContext, "Click on Notifications", Toast.LENGTH_SHORT).show()
                R.id.itemSto-> Toast.makeText(applicationContext, "Click on Storate", Toast.LENGTH_SHORT).show()
            }
            true
        }

    }

    override fun onItemClick(position: Int) {
        var intent = Intent(this, Timeline::class.java)
        intent.putExtra("idalbum", mutableListBaby1[position].idalbum)
        intent.putExtra("nameAlbum", mutableListBaby1[position].name)
        intent.putExtra("idAlbum", mutableListBaby1[position].idalbum)
        intent.putExtra("urlimage", mutableListBaby1[position].urlimage)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }


}