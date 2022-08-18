package ai.ftech.babyphoto.base.service

import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.model.Album
import ai.ftech.babyphoto.model.Data
import ai.ftech.babyphoto.model.Image
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface DataService {
    //4. khai báo link api, phương thức và kiểu dữ liệu trả về

    //get hàm account
    @GET("account.php")
    fun account(): Call<Data<Account>>

    //thêm mới user
    @FormUrlEncoded
    @POST("account_insert.php")
    fun insertAccount(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("firstname") firstname: String,
        @Field("lastname") lastname: String,
    ): Call<Data<String>>

    //cập nhật người dùng
    @FormUrlEncoded
    @POST("Account_update.php")
    fun updateAccount(
        @Field("password") password: String,
        @Field("firstname") firstname: String,
        @Field("lastname") lastname: String,
        @Field("idaccount") idaccount: Int
    ): Call<Data<String>>

    @FormUrlEncoded
    @POST("album.php")
    fun album(@Field("idaccount") idaccount: Int): Call<Data<Album>>

    @FormUrlEncoded
    @POST("album_insert.php")
    fun albumInsert(
        @Field("idaccount") idaccount: Int,
        @Field("urlimage") urlimage: String,
        @Field("name") name: String,
        @Field("gender") gender: Int,
        @Field("birthday") birthday: String,
        @Field("relation") relation: String,
        @Field("amountimage") amountimage: Int
    ): Call<Data<String>>

    @GET("image.php")
    fun image(): Call<Data<Image>>

    @FormUrlEncoded
    @POST("image_insert.php")
    fun imageInsert(
        @Field("idalbum") idalbum: Int,
       // @Part urlimage : MultipartBody.Part,
        @Field("description") description: String,
        @Field("timeline") timeline: String,
    ): Call<Data<String>>

    @Multipart
    @POST("image_test1.php")
    fun imageInsert_muti(
        @Part urlimage : MultipartBody.Part,
    ): Call<ResponseBody>
}