package ai.ftech.babyphoto.base.service

class ApiService {
    //khai báo url
    private val BASE_URL = "https://vuquoccuong.000webhostapp.com/ServerHelloBaby/"
    //khởi tạo retrofit
    private val retrofitClient = RetrofitClient()

    //hàm này dùng cho api nào dùng baseurl
    fun base(): DataService {
        return  retrofitClient.get(BASE_URL)
    }
}