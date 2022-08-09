package ai.ftech.babyphoto.screen.album


import ai.ftech.babyphoto.model.IPreview
import android.content.Intent
import android.graphics.BitmapFactory

class PreviewPresenter(activity : PreviewActivity) : IPreview {
    private val view = activity


    override fun setInsert(uriBaby : String) {
        view.ivBaby.setImageBitmap(BitmapFactory.decodeFile(uriBaby))
    }

}