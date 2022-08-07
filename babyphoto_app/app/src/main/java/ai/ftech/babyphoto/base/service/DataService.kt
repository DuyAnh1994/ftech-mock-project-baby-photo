package ai.ftech.babyphoto.base.service

import ai.ftech.babyphoto.model.Account
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

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
    ) : Call<String>

    @FormUrlEncoded
    @POST("AccountUpdate.php")
    fun setAccountUpdate(
        @Field("password") password: String,
        @Field("firstname") firstname: String,
        @Field("lastname") lastname: String,
        @Field("idaccount") idaccount: Int
    ) : Call<String>

}