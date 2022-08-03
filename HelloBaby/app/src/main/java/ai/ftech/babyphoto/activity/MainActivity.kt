package ai.ftech.babyphoto.activity

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.service.APIService
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAccount()
        //setAccountInsert()
        //setAccountUpdate()
    }

    private fun setAccountUpdate() {
        val dataService = APIService.getService()
        val password : String = ""
        val firstname : String = ""
        val lastname : String = "Quoc Cuong"
        val idaccount : Int = 20010412
        val callback : Call<String> = dataService.setAccountUpdate(password,firstname,lastname,idaccount)
        callback.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {

            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }
        })
    }

    private fun setAccountInsert() {
        val dataService = APIService.getService()
        val email : String = "30464Cuong64IT4@nuce.edu.vn"
        val password : String = "123456"
        val firstname : String = "Vu"
        val lastname : String = "Cuong"
        val idaccount : Int = 20010412
        val callback : Call<String> = dataService.setAccountInsert(email,password,firstname,lastname,idaccount)
        callback.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Toast.makeText(this@MainActivity,response.body(),Toast.LENGTH_LONG).show()

            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }
        })
    }

    private fun getAccount() {
        val dataService = APIService.getService()
        val callback: Call<List<Account>> = dataService.getAccount()
        callback.enqueue(object : Callback<List<Account>> {
            override fun onResponse(
                call: Call<List<Account>>?,
                response: Response<List<Account>>?
            ) {
              val array : ArrayList<Account> = response?.body() as ArrayList<Account>
                Log.d("AAA", "onResponse: ${array[1].email}")
            }

            override fun onFailure(call: Call<List<Account>>, t: Throwable) {

            }

        })
    }
}