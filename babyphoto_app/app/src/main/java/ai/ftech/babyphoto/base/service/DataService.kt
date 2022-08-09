package ai.ftech.babyphoto.base.service

import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.model.Album
import ai.ftech.babyphoto.model.Data
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface DataService {
    //4. khai báo link api, phương thức và kiểu dữ liệu trả về

    //get hàm account
    @GET("account")
    fun account(): Call<List<Data<Account>>>

    //thêm mới user
    @FormUrlEncoded
    @POST("account_insert")
    fun insertAccount(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("firstname") firstname: String,
        @Field("lastname") lastname: String,
        @Field("idaccount") idaccount: Int
    ): Call<List<Data<String>>>

    //cập nhật người dùng
    @FormUrlEncoded
    @POST("Account_update")
    fun updateAccount(
        @Field("password") password: String,
        @Field("firstname") firstname: String,
        @Field("lastname") lastname: String,
        @Field("idaccount") idaccount: Int
    ): Call<List<Data<String>>>

    @GET("album")
    fun album(): Call<List<Data<Album>>>

    @FormUrlEncoded
    @POST("album_insert")
    fun albumInsert(
        @Field("idalbum ") idalbum: Int,
        @Field("idaccount") idaccount: Int,
        @Field("urlimage") urlimage: String,
        @Field("name") name: String,
        @Field("gender") gender: Boolean,
        @Field("birthday") birthday: Date,
        @Field("relation") relation: String,
        @Field("amountimage") amountimage: Int
    ) : Call<List<Data<String>>>
}