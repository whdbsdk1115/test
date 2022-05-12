package kr.ac.dongyang.testapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.invoke.MethodHandleInfo.toString
import java.util.Arrays.toString
import java.util.Objects.toString
import java.util.concurrent.TimeUnit


private const val TAG = "Retrofit"

// 통신할 서버 url
private const val baseUrl = " http://10.0.2.2:3000"


class RetrofitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        // id 선언
        var user = findViewById<TextView>(R.id.user)
        var address = findViewById<TextView>(R.id.address)
        var posx = findViewById<TextView>(R.id.posx)
        var posy = findViewById<TextView>(R.id.posy)
        var food = findViewById<EditText>(R.id.food)
        var price = findViewById<EditText>(R.id.price)
        var taste = findViewById<EditText>(R.id.taste)


        // Retrofit 객체 초기화
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // Json 데이터를 사용자가 정의한java 객체로 변환해주는 라이브러리
            .build() // 레트로핏 구현체 완성!


        val server = retrofit.create(ExampleInterface::class.java) // retrofit 객체 만듦
//        val getRabbit = server.getRabbit()
//        val postFood = server.postFood("당근", "1500", "당근맛")


        server.getRabbit().enqueue(object: Callback<RabbitDto> {
            override fun onFailure(call: Call<RabbitDto>, t: Throwable) {
                Log.e(TAG, "실패" + t.toString())
            }

            override fun onResponse(call: Call<RabbitDto>, response: Response<RabbitDto>) {
                Log.d(TAG, "성공:" ) // + response?.body().toString()

                if(response.isSuccessful.not()) {
                    return
                }
                response.body()?.let {
                    Log.d(TAG, "성공:" + it.toString())
                    //Log.d(TAG, ""+ RabbitDto.toString())
                }
            }
        })


        upload_button.setOnClickListener {

            var food1 = food.text.toString()
            var price1 = price.text.toString()
            var taste1 = taste.text.toString()

            server.postFood(food1, price1, taste1).enqueue(object : Callback<FoodDto> {
                override fun onFailure(call: Call<FoodDto>?, t:Throwable?) {
                    Log.e(TAG, "실패" + t.toString())
                    var dialog = AlertDialog.Builder(this@RetrofitActivity)
                    dialog.setTitle("에러")
                    dialog.setMessage("호출실패~")
                    dialog.show()
                }
                override fun onResponse(call: Call<FoodDto>, response: Response<FoodDto>) {
                    Log.d(TAG, "성공: " + response?.body().toString())
                    var dialog = AlertDialog.Builder(this@RetrofitActivity)
                    dialog.setTitle("성공")
                    dialog.setMessage("호출완료~")
                    dialog.show()
                }
            })
        }
    }

}