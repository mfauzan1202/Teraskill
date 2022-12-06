package id.co.mka.teraskill.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.dataclass.UserInfo
import id.co.mka.teraskill.utils.Resource
import id.co.mka.teraskill.data.responses.AuthResponse
import id.co.mka.teraskill.data.services.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashViewModel: ViewModel() {

    fun refreshToken(body: UserInfo): LiveData<Resource<AuthResponse?>> {
        val mutableLiveData = MutableLiveData<Resource<AuthResponse?>>()

        ApiConfig.getApiService().loginUser(body).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(
                call: Call<AuthResponse>,
                response: Response<AuthResponse>
            ) {
                if (response.isSuccessful) {
                    mutableLiveData.value = Resource.Success(response.body())
                } else {
                    mutableLiveData.value = Resource.Error(null, "Gagal mengirim ulang token", response.code())
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                when (t) {
                    is java.net.SocketTimeoutException -> {
                        mutableLiveData.value = Resource.Error(null, "Koneksi Bermasalah", 0)
                    }
                    is java.net.UnknownHostException -> {
                        mutableLiveData.value = Resource.Error(null, "Server not found", 1)
                    }
                    else -> {

                    }
                }
            }
        })

        return mutableLiveData
    }
}