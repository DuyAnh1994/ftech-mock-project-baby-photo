package ai.ftech.babyphoto.base.service

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class APIRetrofitClient {
    private var retrofit: Retrofit? = null
    fun get(baseUrl: String): DataService {
        if (retrofit == null) {
            val clientHTTP = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build()
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(clientHTTP)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setLenient().create()
                    )
                )
                .build()
        }
        return retrofit!!.create(DataService::class.java)
    }
}