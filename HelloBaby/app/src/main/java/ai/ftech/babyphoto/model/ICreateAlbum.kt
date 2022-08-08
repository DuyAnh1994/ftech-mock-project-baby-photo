package ai.ftech.babyphoto.model

import android.widget.TextView
import java.util.*
interface ICreateAlbum {

    fun getNameAlbum(): String
    fun getGenderAlbum(): Boolean
    fun getBirthdayAlbum(tvBirthday: TextView): String
    fun getRelationAlbum(relation: String): String

}