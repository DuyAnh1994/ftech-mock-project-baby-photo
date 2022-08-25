package ai.ftech.babyphoto.screen.home

import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.AlbumBaby
import ai.ftech.babyphoto.model.Image
import ai.ftech.babyphoto.model.ResponseModel
import ai.ftech.babyphoto.screen.timeline.TimelineState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomePresenter(private val view: IHomeContract.View) {
    private val apiService = APIService.base()

    fun getAlbum(idaccount: Int?) {
        if (idaccount == null) return

        apiService.getAlbumId(idaccount).enqueue(
            object : Callback<ResponseModel<List<AlbumBaby>>> {
                override fun onResponse(
                    call: Call<ResponseModel<List<AlbumBaby>>>,
                    response: Response<ResponseModel<List<AlbumBaby>>>
                ) {
                    val res = response.body() as ResponseModel<MutableList<AlbumBaby>>
                    view.onGetAlbum(HomeState.SUCCESS, "Get album success", res.data)
                }

                override fun onFailure(call: Call<ResponseModel<List<AlbumBaby>>>, t: Throwable) {
                    view.onGetAlbum(HomeState.GET_ALBUM_FAIL, "Get album error", ArrayList())
                }

            }
        )
    }
//    fun getAlbumId(idaccount: Int?){
//        APIService.base().getAlbumId(idaccount as Int).enqueue(
//            object : Callback<ResponseModel<List<AlbumBaby>>> {
//                override fun onResponse(
//                    call: Call<ResponseModel<List<AlbumBaby>>>,
//                    response: Response<ResponseModel<List<AlbumBaby>>>
//                ) {
//                    var res = response.body()!!
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
//    }
}