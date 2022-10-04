package id.co.mka.teraskill

import com.google.gson.annotations.SerializedName

data class ApiResponse(

    @field:SerializedName("message")
    var msg: String,

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("uuid")
    val uuid: String,

    @field:SerializedName("email")
    val email: String
)
