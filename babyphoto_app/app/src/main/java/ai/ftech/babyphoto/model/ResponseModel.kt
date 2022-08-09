package ai.ftech.babyphoto.model

data class ResponseModel<T>(
    var code: String,
    var msg: String,
    var data: T
)
