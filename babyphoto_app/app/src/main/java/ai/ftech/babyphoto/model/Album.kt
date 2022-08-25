package ai.ftech.babyphoto.model

import com.google.gson.annotations.SerializedName


data class Album(
    var idalbum: Int,
    var idaccount: Int,
    var urlimage: String,
    var name: String,
    var gender: Int,
    var birthday: String,
    var relation: String,
    var amountimage: Int
)