package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class AuthResponse(

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
