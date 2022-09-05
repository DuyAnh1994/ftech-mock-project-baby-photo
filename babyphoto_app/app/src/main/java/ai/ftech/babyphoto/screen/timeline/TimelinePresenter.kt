package ai.ftech.babyphoto.screen.timeline

import ai.ftech.babyphoto.data.service.APIService
import ai.ftech.babyphoto.data.model.Image
import ai.ftech.babyphoto.data.model.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TimelinePresenter(private val view: ITimelineContract.View) {
    private val apiService = APIService.base()

    fun getImage(idalbum: String?) {
        if (idalbum == null) return

        apiService.getImageId(idalbum).enqueue(
            object : Callback<ResponseModel<List<Image>>> {
                override fun onResponse(
                    call: Call<ResponseModel<List<Image>>>,
                    response: Response<ResponseModel<List<Image>>>
                ) {
                    val res = response.body() as ResponseModel<MutableList<Image>>
                    view.onGetImage(TimelineState.SUCCESS, "Get image success", res.data)
                }

                override fun onFailure(call: Call<ResponseModel<List<Image>>>, t: Throwable) {
                    view.onGetImage(TimelineState.GET_IMAGE_FAILED, "Get image error", ArrayList())
                }

            }
        )
    }
}