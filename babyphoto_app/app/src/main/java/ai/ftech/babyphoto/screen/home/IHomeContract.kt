package ai.ftech.babyphoto.screen.home

import ai.ftech.babyphoto.data.model.AlbumBaby

interface IHomeContract {
    interface View{
        fun onGetAlbum(state: HomeState, message: String, lAbum: List<AlbumBaby>)
    }
}