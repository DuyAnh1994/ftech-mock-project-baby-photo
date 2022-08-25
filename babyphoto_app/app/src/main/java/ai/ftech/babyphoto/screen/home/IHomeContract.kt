package ai.ftech.babyphoto.screen.home

import ai.ftech.babyphoto.model.AlbumBaby

interface IHomeContract {
    interface View{
        fun onGetAlbum(state: HomeState, message: String, lAbum: List<AlbumBaby>)
    }
}