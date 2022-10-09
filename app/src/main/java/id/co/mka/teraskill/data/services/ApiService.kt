package id.co.mka.teraskill.data.services

import id.co.mka.teraskill.data.responses.AuthResponse
import id.co.mka.teraskill.UserInfo
import id.co.mka.teraskill.data.responses.EditProfileResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("user")
    fun registerUser(@Body response: UserInfo): Call<AuthResponse>

    @Headers("Content-Type: application/json")
    @POST("login")
    fun loginUser(@Body response: UserInfo): Call<AuthResponse>

    @Headers("Content-Type: application/json")
    @PATCH("user/{uuid}")
    fun updateUser(
        @Path("uuid") uuid: Int,
        @Body response: UserInfo): Call<EditProfileResponse>

    @POST("gabung-mentor")
    fun uploadFile(
        @Body body: RequestBody
    ): Call<AuthResponse>
}