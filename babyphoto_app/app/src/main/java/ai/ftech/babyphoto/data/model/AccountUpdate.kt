package ai.ftech.babyphoto.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AccountUpdate(
    @SerializedName("email")
    @Expose
    var email: String,
    @SerializedName("firstname")
    @Expose
    var firstname: String,
    @SerializedName("lastname")
    @Expose
    var lastname: String,
    @SerializedName("idaccount")
    @Expose
    var idaccount: Int
)
