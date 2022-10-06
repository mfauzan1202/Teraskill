package id.co.mka.teraskill

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("user")
    fun registerUser(@Body response: UserInfo): Call<ApiResponse>

    @Headers("Content-Type: application/json")
    @POST("login")
    fun loginUser(@Body response: UserInfo): Call<ApiResponse>

    @POST("gabung-mentor")
    fun uploadFile(
        @Body body: RequestBody
    ): Call<ApiResponse>
}