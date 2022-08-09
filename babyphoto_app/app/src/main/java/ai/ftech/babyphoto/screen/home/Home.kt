package ai.ftech.babyphoto.screen.home

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.AlbumBaby
import ai.ftech.babyphoto.model.ResponseModel
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : AppCompatActivity() {
    @SuppressLint("WrongViewCast", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val recycleBaby: RecyclerView = findViewById(R.id.rcvHomeViewBaby)

        recycleBaby.layoutManager = LinearLayoutManager(this)
        val mutableListBaby: MutableList<AlbumBaby> = ArrayList()
//        mutableListBaby.add(AlbumBaby("", "nomo1", "4", "items", "", "", "", ""))
//
        recycleBaby.adapter =
            BabyHomeAdapter(this@Home, mutableListBaby, "Add Baby Test")
        val manager = GridLayoutManager(this@Home, 2, GridLayoutManager.VERTICAL, false)
        recycleBaby.layoutManager = manager

        APIService().base().getAlbum().enqueue(
            object : Callback<ResponseModel<List<AlbumBaby>>> {
                override fun onResponse(
                    call: Call<ResponseModel<List<AlbumBaby>>>,
                    response: Response<ResponseModel<List<AlbumBaby>>>
                ) {
                    val mutableListBaby: MutableList<AlbumBaby> = ArrayList()
                    val res = response.body() as ResponseModel<List<AlbumBaby>>

                    mutableListBaby.addAll(res.data)

                    recycleBaby.adapter =
                        BabyHomeAdapter(this@Home, mutableListBaby, "Add Baby Test")
//                    val manager = GridLayoutManager(this@Home, 2, GridLayoutManager.VERTICAL, false)
//                    recycleBaby.layoutManager = manager
                }

                override fun onFailure(call: Call<ResponseModel<List<AlbumBaby>>>, t: Throwable) {
                    Toast.makeText(this@Home, "Get album failed", Toast.LENGTH_SHORT).show()
                }

            }
        )
    }
}