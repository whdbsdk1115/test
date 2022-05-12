package kr.ac.dongyang.testapplication

import android.content.ContentValues.TAG
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    companion object {
        // 통신할 서버 url
        private const val baseUrl = " http://10.0.2.2:3000"


        // Retrofit 객체 초기화
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(this.baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // Json 데이터를 사용자가 정의한java 객체로 변환해주는 라이브러리
            .build() // 레트로핏 구현체 완성!

        val FOOD = R.id.food
        val PRICE = R.id.price
        val TASTE = R.id.taste

        private val server: ExampleInterface = retrofit.create(ExampleInterface::class.java) // retrofit 객체 만듦
        private val getRabbit = server.getRabbit()


//private val postFood = server.postFood(FOOD, PRICE, TASTE, "ko", "en", "테스트")

    }



//    exampleInterface.getRabbit("")
//    .enqueue(object: Callback<RabbitDto>{
//        override fun onFailure(call: Call<RabbitDto>, t: Throwable) {
//            Log.d(TAG, t.toString())
//        }
//
//        override fun onResponse(call: Call<RabbitDto>, response: Response<RabbitDto>) {
//            if(response.isSuccessful.not()) {
//                return
//            }
//            response.body()?.let {
//                Log.d(TAG, it.toString())
//
//                it.books.forEach { }
//            }
//        }
//    })
}