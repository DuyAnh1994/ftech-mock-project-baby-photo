package ai.ftech.babyphoto.base.service

import ai.ftech.babyphoto.model.AccountModel
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface DataService {
    //4. khai báo link api, phương thức và kiểu dữ liệu trả về

    //get hàm account
    @GET("Account.php")
    fun account(): Call<List<AccountModel>>

//    @GET("20tu/2?id={id}&qtype={qtype}")
//    fun getValue(
//        @Path("id") id: String,
//        @Query("qtype") qtype: String
//    ): Call<Any>

    //thêm mới user
    @POST("AccountInsert.php")
    fun insertAccount(
        @Body body: RequestBody
    ): Call<ResponseBody>
    @FormUrlEncoded
    @POST("AccountInsert.php")
    fun insertAccount(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("firstname") firstName: String,
        @Field("lastname") lastName: String,
        @Field("idaccount") idAccount: Int
    ): Call<String>

    //cập nhật người dùng
    @FormUrlEncoded
    @POST("AccountUpdate.php")
    fun updateAccount(
        @Body
        password: String,
        firstname: String,
        lastname: String,
        idaccount: Int
    ): Call<String>
}