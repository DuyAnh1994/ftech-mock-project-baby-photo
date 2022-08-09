package ai.ftech.babyphoto.model


data class Album(
    val idalbum: Int,
    val idaccount: Int,
    val urlimage: String,
    val name: String,
    val gender: Int,
    val birthday: String,
    val relation: String,
    val amountimage: Int
)