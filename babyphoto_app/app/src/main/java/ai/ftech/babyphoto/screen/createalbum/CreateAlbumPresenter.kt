package ai.ftech.babyphoto.screen.createalbum

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Data
import android.app.ProgressDialog
import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CreateAlbumPresenter() : ICreateContract.ICreatePresenter {

    private var mView: ICreateContract.ICreateView? = null

    fun setView(view: ICreateContract.ICreateView) {
        this.mView = view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createAlbum(
        singleFile: MultipartBody.Part,
        rqIdaccount: RequestBody, rqName: RequestBody,
        rqGender: RequestBody, rqBirthday: RequestBody,
        rqRelation: RequestBody, mThis: CreateAlbumActivity
    ) {
        val progressdialog = ProgressDialog(mThis, R.style.AppCompatAlertDialogStyle)
        progressdialog.setMessage("Updating")
        progressdialog.setCancelable(false)
        progressdialog.show()

        val dataService = APIService.base()
        val callback = dataService.albumInsert(
            singleFile,
            rqIdaccount,
            rqName,
            rqGender,
            rqBirthday,
            rqRelation
        )

        callback.enqueue(object : Callback<Data<String>> {
            override fun onResponse(
                call: Call<Data<String>>,
                response: Response<Data<String>>
            ) {
                if (response.body()!!.code == "code13") {
                    mView?.onSuccess(response.body()!!.msg)
                    progressdialog.dismiss()
                } else {
                    mView?.onError(response.body()!!.msg)
                    progressdialog.dismiss()
                }
            }

            override fun onFailure(call: Call<Data<String>>, t: Throwable) {
                mView?.onFail(t.message!!)
                progressdialog.dismiss()
            }

        })
    }


}

