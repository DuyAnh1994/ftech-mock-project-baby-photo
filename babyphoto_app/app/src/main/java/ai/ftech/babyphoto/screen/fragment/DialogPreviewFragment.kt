package ai.ftech.babyphoto.screen.fragment

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.screen.album.PhotoFolderActivity
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogPreviewFragment :  BottomSheetDialogFragment() {
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var ivCancel: ImageView
    private lateinit var ivOk: ImageView
    private lateinit var ivBaby: ImageView
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetDialog =
            BottomSheetDialog(requireContext())
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.preview_activity, null)
        bottomSheetDialog.setContentView(view)
        ivCancel = view.findViewById(R.id.ivPreviewCancel)
        ivOk = view.findViewById(R.id.ivPreviewOk)
        ivBaby = view.findViewById(R.id.ivPreviewBaby)

        val urlImage = arguments?.getString("urlImage")
        ivBaby.setImageBitmap(BitmapFactory.decodeFile(urlImage))
            setOnClick(BitmapFactory.decodeFile(urlImage))
        return bottomSheetDialog
    }

    private fun setOnClick(urlImage: Bitmap) {
        ivCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        ivOk.setOnClickListener {
            iPreviewUri.getBitmap(urlImage)
            bottomSheetDialog.dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IPreviewUri) {
            iPreviewUri = context
        }
    }

    lateinit var iPreviewUri: IPreviewUri
    interface IPreviewUri {
        fun getBitmap(Uri: Bitmap)
    }

}