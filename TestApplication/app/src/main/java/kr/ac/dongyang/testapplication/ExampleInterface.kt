package kr.ac.dongyang.testapplication

import retrofit2.Call
import retrofit2.http.*

interface ExampleInterface {

    @GET("/users/rabbit")
    fun getRabbit(): Call<RabbitDto>

    @FormUrlEncoded
    @POST("/store/food")
    fun postFood(
//            @Body foodInfo: FoodDto
        @Field("food") food: String,
        @Field("price") price: String,
        @Field("taste") taste: String
    ): Call<FoodDto>

}