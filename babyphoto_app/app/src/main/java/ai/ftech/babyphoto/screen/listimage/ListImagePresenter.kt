package ai.ftech.babyphoto.screen.listimage

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Data
import ai.ftech.babyphoto.model.DataResult
import android.app.ProgressDialog
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListImagePresenter() : IListContract.IPresenter {

    private var mView: IListContract.IView? = null


    fun setView(v: IListContract.IView) {
        this.mView = v
    }

    override fun addMultiImageToServer(
        files: MutableList<MultipartBody.Part>,
        idalbum: RequestBody,
        description: RequestBody,
        timeline: RequestBody,
        ID_ALBUM: Int,
        mThis: ListImageActivity
    ) {
        val progressdialog = ProgressDialog(mThis, R.style.AppCompatAlertDialogStyle)
        progressdialog.setMessage("Updating")
        progressdialog.setCancelable(false)
        progressdialog.show()
        val dataService = APIService.base()
        val callback = dataService.imageInsertMulti(files, idalbum, description, timeline)
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

    override fun addImageSingleToServer(
        singFile: MultipartBody.Part,
        idalbum: RequestBody,
        description: RequestBody,
        timeline: RequestBody,
        mThis: ListImageActivity
    ) {
        val progressdialog = ProgressDialog(mThis, R.style.AppCompatAlertDialogStyle)
        progressdialog.setMessage("Updating")
        progressdialog.setCancelable(false)
        progressdialog.show()


        val dataService = APIService.base()
        val callback = dataService.imageInsertSingle(singFile, idalbum, description, timeline)
        callback.enqueue(object : Callback<Data<String>> {
            override fun onResponse(
                call: Call<Data<String>>,
                response: Response<Data<String>>
            ) {

                if (response.body()!!.code == "code13") {
//                    mView?.onSuccess(response.body()!!.msg)
                    val data = DataResult<List<String>>()
                    data.state = DataResult.State.SUCCESS
                    data.data = response.body()!!.data
                    mView?.onList(data)
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