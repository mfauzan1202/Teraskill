package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class AuthResponse(

    @field:SerializedName("msg")
    var msg: String,

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("uuid")
    val uuid: String,

    @field:SerializedName("accessToken")
    val accessToken: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("no_hp")
    val no_hp: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("confpassword")
    val confpassword: String,

    @field:SerializedName("avatar")
    val avatar: String
){
    data class UpdateAvatarResponse(
        @field:SerializedName("status")
        val status: String,

        @field:SerializedName("newAvatar")
        val newAvatar: String
    )
}
