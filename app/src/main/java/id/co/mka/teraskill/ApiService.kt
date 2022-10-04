package id.co.mka.teraskill

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("user")
    fun registerUser(@Body response: UserInfo): Call<ApiResponse>

    @Headers("Content-Type: application/json")
    @POST("login")
    fun loginUser(@Body response: UserInfo): Call<ApiResponse>

    @Multipart
    @POST("gabung-mentor")
    fun uploadFile(
        @Part surat_pernyataan: MultipartBody.Part,
        @Part portofolio: MultipartBody.Part
    ): Call<ApiResponse>
}