package ai.ftech.babyphoto.service

class APIService {
    companion object {
        private val BASE_URL = "https://vuquoccuong.000webhostapp.com/ServerHelloBaby/"

        fun getService(): DataService {
            return APIRetrofitClient.getClient(BASE_URL).create(DataService::class.java)
        }
    }
}