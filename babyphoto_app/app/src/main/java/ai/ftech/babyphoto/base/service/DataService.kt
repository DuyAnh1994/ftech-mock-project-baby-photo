package ai.ftech.babyphoto.base.service

import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.model.Album
import ai.ftech.babyphoto.model.Data
import ai.ftech.babyphoto.model.Image
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @Multipart
    @POST("album_insert.php")
    fun albumInsert(
        @Part file: MultipartBody.Part,
        @Part("idaccount") idaccount: RequestBody,
        @Part("name") name: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("birthday") birthday: RequestBody,
        @Part("relation") relation: RequestBody,
    ): Call<Data<String>>

    @GET("image.php")
    fun image(): Call<Data<Image>>

    @Multipart
    @POST("image_insert_multi.php")
    fun imageInsertMulti(
        @Part files: MutableList<MultipartBody.Part>,
        @Part("idalbum") idalbum: RequestBody,
        @Part("description") description: RequestBody,
        @Part("timeline") timeline: RequestBody,
    ): Call<Data<String>>

    @Multipart
    @POST("image_insert_single.php")
    fun imageInsertSingle(
        @Part file: MultipartBody.Part,
        @Part("idalbum") idalbum: RequestBody,
        @Part("description") description: RequestBody,
        @Part("timeline") timeline: RequestBody,
    ): Call<Data<String>>
}