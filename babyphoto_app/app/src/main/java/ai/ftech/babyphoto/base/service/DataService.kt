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

    //get hàm account
    @GET("account.php")
    fun account(): Call<ResponseModel<List<Account>>>

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
    @POST("account_update.php")
    fun updateAccount(
        @Field("email") email: String,
        @Field("firstname") firstName: String,
        @Field("lastname") lastName: String,
        @Field("idaccount") idaccount: Int
    ): Call<String>

    //cập nhật password
    @FormUrlEncoded
    @POST("account_pw.php")
    fun updatePass(
        @Field("idaccount") idaccount: Int,
        @Field("password") password: String
    ): Call<String>

    @FormUrlEncoded
    @POST("album.php")
    fun getAlbumId(@Field("idaccount") idaccount: Int): Call<ResponseModel<List<AlbumBaby>>>

    //list image
    @FormUrlEncoded
    @POST("image.php")
    fun getImageId(@Field("idalbum") idalbum: Any?): Call<ResponseModel<List<Image>>>

    //get accountid
    @FormUrlEncoded
    @POST("account_id.php")
    fun getAccountId(@Field("idaccount") idaccount: Int): Call<ResponseModel<List<Account>>>
}