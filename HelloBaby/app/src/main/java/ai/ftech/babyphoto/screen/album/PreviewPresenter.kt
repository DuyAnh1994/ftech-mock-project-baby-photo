package ai.ftech.babyphoto.screen.album


import ai.ftech.babyphoto.model.IPreview
import android.content.Intent
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

    override fun setIntent(uriBaby : String) {
        view.ivOk.setOnClickListener {
            val intent = Intent(view, PhotoFolderActivity::class.java)
            intent.putExtra("uriImage",uriBaby )
            view.startActivity(intent)
        }
    }
}