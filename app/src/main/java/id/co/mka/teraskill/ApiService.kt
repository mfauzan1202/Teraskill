package id.co.mka.teraskill

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("user")
    fun addUser(@Body response: UserInfo): Call<ApiResponse>
}