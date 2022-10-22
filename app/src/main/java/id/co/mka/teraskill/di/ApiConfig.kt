package id.co.mka.teraskill.di

import id.co.mka.teraskill.BuildConfig
import id.co.mka.teraskill.BuildConfig.BASE_URL
import id.co.mka.teraskill.data.services.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiConfig {

    fun getApiService(authToken: String? = null): ApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $authToken")
                        .build()
                )
            }.also {
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    it.addInterceptor(logging)
                }
            }
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}
