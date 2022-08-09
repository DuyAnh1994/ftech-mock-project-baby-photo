package ai.ftech.babyphoto.model

import android.widget.TextView
import java.util.*
interface ICreateAlbum {

    fun getGenderAlbum() : Int
    fun getBirthdayAlbum(tvBirthday: TextView)
    fun getRelationAlbum(relation: String)

}