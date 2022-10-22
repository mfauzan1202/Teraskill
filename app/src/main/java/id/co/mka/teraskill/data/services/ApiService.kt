package id.co.mka.teraskill.data.services

import id.co.mka.teraskill.data.dataclass.UserInfo
import id.co.mka.teraskill.data.responses.*
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
        @Path("uuid") uuid: String,
        @Body response: UserInfo
    ): Call<AuthResponse>

    @POST("gabung-mentor")
    fun uploadFile(
        @Body body: RequestBody
    ): Call<AuthResponse>

    @GET("kelas-user/progress")
    fun getProgress(): Call<ClassProgressResponse>

    @GET("kelas-user/transaksi")
    fun getHistoryTransaction(): Call<TransactionHistoryResponse>

    @GET("kelas")
    fun getAllClassInfo(): Call<List<ClassResponseItem>>

    @GET("kelas/{uuid}")
    fun getClassInfoByID(
        @Path("uuid") uuid: String
    ): Call<ClassResponseItem>
}