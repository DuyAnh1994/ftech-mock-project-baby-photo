package ai.ftech.babyphoto.screen.createalbum

import android.widget.TextView

interface ICreateAlbum {

    fun getGenderAlbum() : Int
    fun getBirthdayAlbum(tvBirthday: TextView)
    fun getRelationAlbum(relation: String)

}