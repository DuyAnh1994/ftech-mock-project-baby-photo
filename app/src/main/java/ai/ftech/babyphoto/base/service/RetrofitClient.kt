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

class RetrofitClient {
    //3. khai báo biến retrofit
    private var retrofit: Retrofit? = null

    //khai báo khởi tạo webservice với baseUrl
    fun get(baseUrl: String): DataService {
        //nếu chưa khởi tạo retrofit thì khởi tạo
        if (retrofit == null) {
            val clientHTTP = OkHttpClient.Builder()
                //30s không kết nối được sẽ tự tắt
                .connectTimeout(30, TimeUnit.SECONDS)
                //có kết nối lại sau khi kết nối thất bại không(auto reconnect)
                .retryOnConnectionFailure(false)
                .build()
            retrofit = Retrofit.Builder()
                //khai báo baseUrl
                .baseUrl(baseUrl)
                //cài đặt cấu hình request
                .client(clientHTTP)
                //thêm kiểu convert dữ liệu
                //mình dùng cái này GsonConverterFactory.create(GsonBuilder().setLenient().create())
                // vì nếu data trả ra không phải json thì vẫn đọc được
                //api post của nó đang trả về text
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