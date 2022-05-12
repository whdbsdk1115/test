package kr.ac.dongyang.testapplication

import com.google.gson.annotations.SerializedName


data class RabbitDto(
    @SerializedName("user")
    val user: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("posx")
    val posx: Int,
    @SerializedName("posy")
    val posy: Int
    // 속성과 변수명이 같으면 @SerializedName 안써도 됨! 하지만 혹시 모르니 쓰는 게 좋음
)

//data class FoodRequestDto (
//    val food: String,
//    val price: String,
//    val taste: String
//)

data class FoodDto (
        @SerializedName("food") val food: String,
        @SerializedName("price") val price: String,
        @SerializedName("taste") val taste: String
)