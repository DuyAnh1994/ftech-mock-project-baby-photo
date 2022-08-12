package ai.ftech.babyphoto.model

data class Image(
    val idimage: Int,
    val idalbum: Int,
    val urlimage: String,
    val description: String,
    val timeline: String
)