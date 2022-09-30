package ai.ftech.babyphoto.screen

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.screen.register.EnterEmailActivity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView

class HeaderView @JvmOverloads constructor(context: Context, attibutes: AttributeSet) : LinearLayout(context,
    attibutes) {
    private val titleHeader: TextView
    private val ibBackActivity: ImageButton
    init {
        val view = LayoutInflater.from(context).inflate(R.layout.header_custom, this, true)
        titleHeader = view.findViewById(R.id.tvHeaderCustom)
        ibBackActivity = view.findViewById(R.id.ibHeaderCustomBackActivity)
    }
    fun setTitle(title:String){
        this.titleHeader.text = title
    }
    fun setAction(now: String, start: String){
        ibBackActivity

    }

}
