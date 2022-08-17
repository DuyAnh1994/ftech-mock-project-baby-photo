package ai.ftech.babyphoto.base.service

import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.model.AlbumBaby
import ai.ftech.babyphoto.model.Image
import ai.ftech.babyphoto.model.ResponseModel
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Field
import retrofit2.http.*

interface DataService {
    //4. khai báo link api, phương thức và kiểu dữ liệu trả về

    //get hàm account
    @GET("account.php")
    fun account(): Call<ResponseModel<List<Account>>>

//    @GET("20tu/2?id={id}&qtype={qtype}")
//    fun getValue(
//        @Path("id") id: String,
//        @Query("qtype") qtype: String
//    ): Call<Any>

    //thêm mới user
    @POST("account_insert.php")
    fun insertAccount(
        @Body body: RequestBody
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("account_insert.php")
    fun insertAccount(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("firstname") firstName: String,
        @Field("lastname") lastName: String,
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

    //lấy danh sách album
//    @GET("album.php")
//    fun getAlbum(): Call<ResponseModel<List<AlbumBaby>>>
    @FormUrlEncoded
    @POST("album.php")
    fun getAlbumId(@Field("idaccount") idaccount: Int): Call<ResponseModel<List<AlbumBaby>>>
    //list image
    @FormUrlEncoded
    @POST("image.php")
    fun getImageId(@Field("idalbum") idalbum: Any?): Call<ResponseModel<List<Image>>>
}