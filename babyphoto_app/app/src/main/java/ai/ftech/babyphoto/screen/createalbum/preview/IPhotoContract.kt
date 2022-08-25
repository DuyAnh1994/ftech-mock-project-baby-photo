package ai.ftech.babyphoto.screen.createalbum.preview

interface IPhotoContract {

    interface IView {
        fun data(arrayImage: MutableList<String>)
    }

    interface IPresenter {
        fun openBackDialog(mThis: PhotoFolderActivity)
        fun getImage(mThis: PhotoFolderActivity)
    }

}