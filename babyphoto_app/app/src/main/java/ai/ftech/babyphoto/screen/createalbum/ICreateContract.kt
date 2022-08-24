package ai.ftech.babyphoto.screen.createalbum

import ai.ftech.babyphoto.model.IBaseView
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ICreateContract {
    interface ICreateView : IBaseView {

    }

    interface ICreatePresenter {

        fun createAlbum(
            singleFile: MultipartBody.Part,
            rqIdaccount: RequestBody, rqName: RequestBody,
            rqGender: RequestBody, rqBirthday: RequestBody,
            rqRelation: RequestBody, mThis: CreateAlbumActivity
        )
    }
}