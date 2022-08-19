package ai.ftech.babyphoto.base.service

class APIService {
    private val BASE_URL = "http://vuquoccuong.000webhostapp.com/ServerHelloBaby/"
    private val retrofitClient = APIRetrofitClient()
    fun base(): DataService {
        return  retrofitClient.get(BASE_URL)
    companion object {
          private val BASE_URL = "https://vuquoccuong.000webhostapp.com/ServerHelloBaby/"
        fun base(): DataService {
            return APIRetrofitClient.getClient(BASE_URL).create(DataService::class.java)
        }
    }
}