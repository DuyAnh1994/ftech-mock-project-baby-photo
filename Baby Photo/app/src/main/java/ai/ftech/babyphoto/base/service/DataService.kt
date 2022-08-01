package ai.ftech.babyphoto.base.service

import ai.ftech.babyphoto.model.AccountModel
import retrofit2.Call
import retrofit2.http.*

interface DataService {
    //4. khai báo link api, phương thức và kiểu dữ liệu trả về

    //get hàm account
    @GET("Account.php")
    fun account(): Call<List<AccountModel>>

//    @GET("http://localhost:3000/20tu/{id}")
//    fun getValue(
//        @Path("id") id : String,
//        @Query("name") name: String): Call<List<T>>

    //thêm mới user
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