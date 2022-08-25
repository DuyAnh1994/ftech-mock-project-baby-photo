package ai.ftech.babyphoto.screen.timeline

import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Image
import ai.ftech.babyphoto.model.ResponseModel
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_timeline.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.format.DateTimeFormatter

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
                    val res = response.body() as ResponseModel<List<Image>>
                    view.onGetImage(TimelineState.SUCCESS, "Get image success", res.data)
                }

                override fun onFailure(call: Call<ResponseModel<List<Image>>>, t: Throwable) {
                    view.onGetImage(TimelineState.GET_IMAGE_FAILED, "Get image error", ArrayList())
                }

            }
        )
    }
}