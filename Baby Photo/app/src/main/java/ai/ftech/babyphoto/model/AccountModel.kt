package ai.ftech.babyphoto.model

//khai báo các trường có trong đối tượng
data class AccountModel (
    val email: String,
    var password: String,
    var firstname: String,
    val lastname: String,
    var idaccount: Int
)