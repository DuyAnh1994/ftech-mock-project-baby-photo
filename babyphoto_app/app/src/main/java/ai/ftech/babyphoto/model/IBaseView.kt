package ai.ftech.babyphoto.model

interface IBaseView {

    fun onSuccess(msg : String)
    fun onFail(msg : String)
    fun onError(msg : String)
}