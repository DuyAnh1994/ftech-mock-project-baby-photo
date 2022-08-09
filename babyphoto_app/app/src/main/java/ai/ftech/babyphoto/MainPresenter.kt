package ai.ftech.babyphoto

import ai.ftech.babyphoto.base.service.APIService
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

//lấy file này để xử lý logic
class MainPresenter(mainActivity: MainActivity) {
    //khai báo service
    private val apiService = APIService.base()
    private val view = mainActivity

    //hàm gọi lấy danh sách tài khoản
//    fun getAccount() {
//
//        apiService.account().enqueue(
//            object : Callback<List<AccountModel>> {
//                override fun onResponse(
//                    call: Call<List<AccountModel>>,
//                    response: Response<List<AccountModel>>
//                ) {
//                    //thành công thì update text
////                    view.tvListAccount.text = response.body().toString()
//
//                    Log.d("TAG", "onResponse: ${response.body()?.get(0)?.email}")
//                }
//
//                override fun onFailure(call: Call<List<AccountModel>>, t: Throwable) {
//                    view.tvListAccount.text = "Có lỗi xảy ra"
//                    Log.e("ERROR",t.toString())
//                }
//
//            }
//        )
//    }

//    fun insertAccount() {
//        //kiểm tra xem có trống các trường cần nhập không
//        if (view.edtFirstName.text.toString().trim().isEmpty() || view.edtLastName.text.toString()
//                .trim().isEmpty()
//        )
//            return
//
//        //random id
//        val idRd = Random().nextInt(10000000)
//
//        apiService.insertAccount(
//            "LinhCute@gmail.com",
//            "password",
//            view.edtFirstName.text.toString(),
//            view.edtLastName.text.toString(),
//            idRd
//        ).enqueue(
//            object : Callback<String> {
//                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    view.tvMethodPost.text = response.body()
//                }
//
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                    view.tvMethodPost.text = "Thêm mới thất bại"
//                    Log.e("ERROR",t.toString())
//                }
//            }
//        )
//    }
}