package ai.ftech.babyphoto.screen.presenter

import ai.ftech.babyphoto.model.IPreview
import ai.ftech.babyphoto.screen.activity.PreviewActivity
import android.graphics.BitmapFactory

class PreviewPresenter(activity : PreviewActivity) : IPreview {
    private val view = activity
    override fun setCancel() {
      view.ivCancel.setOnClickListener {
          view.openBackDialog()
      }
    }

    override fun setInsert(uriBaby : String) {
        view.ivBaby.setImageBitmap(BitmapFactory.decodeFile(uriBaby))
    }
}