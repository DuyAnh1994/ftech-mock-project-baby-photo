package ai.ftech.babyphoto.screen.listimage

import ai.ftech.babyphoto.model.IBaseView
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface IListContract {
    interface IListImageView : IBaseView

    interface IListImagePresenter {
        fun addMultiImageToServer(
            files: MutableList<MultipartBody.Part>,
            idalbum: RequestBody,
            description: RequestBody,
            timeline: RequestBody,
            ID_ALBUM: Int,
            mThis: ListImageActivity
        )

        fun addImageSingleToServer(
            singFile: MultipartBody.Part,
            idalbum: RequestBody,
            description: RequestBody,
            timeline: RequestBody,
            mThis: ListImageActivity
        )
    }
}