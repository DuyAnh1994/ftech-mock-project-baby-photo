package ai.ftech.babyphoto.base.service

import ai.ftech.babyphoto.model.Account
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.*

interface DataService {

    @GET("Account.php")
    fun getAccount(): Call<List<Account>>

    @FormUrlEncoded
    @POST("AccountInsert.php")
    fun setAccountInsert(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("firstname") firstname: String,
        @Field("lastname") lastname: String,
        @Field("idaccount") idaccount: Int
    ): Call<String>

    @FormUrlEncoded
    @POST("AccountUpdate.php")
    fun setAccountUpdate(
        @Field("password") password: String,
        @Field("firstname") firstname: String,
        @Field("lastname") lastname: String,
        @Field("idaccount") idaccount: Int
    ): Call<String>

    @FormUrlEncoded
    @POST("AlbumInsert.php")
    fun setAlbumInsert(
        @Field("idalbum ") idalbum: Int,
        @Field("idaccount") idaccount: Int,
        @Field("urlimage") urlimage: String,
        @Field("name") name: String,
        @Field("gender") gender: Boolean,
        @Field("birthday") birthday: Date,
        @Field("relation") relation: String,
        @Field("amountimage") amountimage: Int
    ) : Call<String>

}