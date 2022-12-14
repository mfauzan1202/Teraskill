package id.co.mka.teraskill.ui.auth.forgot_pass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.AuthResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPasswordViewModel(private val apiService: ApiService) : ViewModel() {

    fun changePassword(
        email: String,
        password: String,
        password_confirmation: String
    ): LiveData<Resource<AuthResponse>> {
        val mutableLiveData = MutableLiveData<Resource<AuthResponse>>()

        val bodyRequest = HashMap<String, String>()
        bodyRequest["email"] = email
        bodyRequest["password"] = password
        bodyRequest["confpassword"] = password_confirmation

        apiService.changePassword(bodyRequest).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        mutableLiveData.value = Resource.Success(body)
                    }else{
                        mutableLiveData.value = Resource.Error(null, response.message(), response.code())
                    }
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