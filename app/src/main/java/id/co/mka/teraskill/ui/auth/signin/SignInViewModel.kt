package id.co.mka.teraskill.ui.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.dataclass.UserInfo
import id.co.mka.teraskill.data.responses.AuthResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel(private val apiService: ApiService) : ViewModel() {

    fun loginUser(bodyRequest: UserInfo): LiveData<Resource<AuthResponse?>> {
        val mutableLiveData = MutableLiveData<Resource<AuthResponse?>>()

        apiService.loginUser(bodyRequest).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    mutableLiveData.value = Resource.Success(response.body())
                } else {
                    mutableLiveData.value =
                        Resource.Error(null, response.message(), response.code())
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                when (t) {
                    is java.net.SocketTimeoutException -> {
                        mutableLiveData.value = Resource.Error(null, "Koneksi Bermasalah", 0)
                    }
                    is java.net.UnknownHostException -> {
                        mutableLiveData.value = Resource.Error(null, "Koneksi Bermasalah", 1)
                    }
                    is java.net.ConnectException -> {
                        mutableLiveData.value = Resource.Error(null, "Koneksi Bermasalah", 2)
                    }
                    else -> {
                        mutableLiveData.value = Resource.Error(null, "Koneksi Bermasalah", 3)
                    }
                }
            }
        })
        return mutableLiveData
    }
}