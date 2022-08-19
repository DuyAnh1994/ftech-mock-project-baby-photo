package ai.ftech.babyphoto.model

data class Image(
    var idimage: Int,
    var idalbum: Int,
    var urlimage: String,
    var description: String,
    var timeline: String
)