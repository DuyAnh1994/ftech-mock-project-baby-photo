package ai.ftech.babyphoto.model

import android.view.View
import android.widget.TextView
import java.util.*

interface ICreateAlbum {

  fun getNameAlbum() : String
  fun getGenderAlbum() : Int
  fun getBirthdayAlbum(tvBirthday : TextView) : String
  fun getRelationAlbum() : String
}