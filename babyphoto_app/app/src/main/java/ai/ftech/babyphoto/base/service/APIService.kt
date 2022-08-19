package ai.ftech.babyphoto.base.service

class APIService {
    private val BASE_URL = "http://vuquoccuong.000webhostapp.com/ServerHelloBaby/"
    private val retrofitClient = APIRetrofitClient()
    fun base(): DataService {
        return  retrofitClient.get(BASE_URL)
    }
}