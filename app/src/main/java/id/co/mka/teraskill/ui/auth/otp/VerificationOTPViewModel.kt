package id.co.mka.teraskill.ui.auth.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.AuthResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerificationOTPViewModel(private val apiService: ApiService) : ViewModel() {

    fun sendOTP(email: String, token: Int): LiveData<Resource<AuthResponse?>> {
        val mutableLiveData = MutableLiveData<Resource<AuthResponse?>>()

        val otpRequest = HashMap<String, Any>()
        otpRequest["email"] = email
        otpRequest["token"] = token

        apiService.verifyEmail(otpRequest).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    mutableLiveData.value = Resource.Success(response.body())
                } else {
                    mutableLiveData.value =
                        Resource.Error(null, "Gagal mengirim ulang token", response.code())
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

    fun resendOTP(email: String): LiveData<Resource<AuthResponse?>> {
        val mutableLiveData = MutableLiveData<Resource<AuthResponse?>>()

        val otpRequest = HashMap<String, Any>()
        otpRequest["email"] = email

        apiService.resendVerifyEmail(otpRequest)
            .enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    if (response.isSuccessful) {
                        mutableLiveData.value = Resource.Success(response.body())
                    } else {
                        mutableLiveData.value =
                            Resource.Error(null, "Gagal mengirim ulang token", response.code())
                    }

                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    when (t) {
                        is java.net.SocketTimeoutException -> {
                            mutableLiveData.value = Resource.Error(null, "Koneksi Bermasalah", 0)
                        }
                        is java.net.UnknownHostException -> {
                            mutableLiveData.value = Resource.Error(null, "", 1)
                        }
                        else -> {

                        }
                    }
                }
            })
        return mutableLiveData
    }

    fun sendOTPResetPassword(email: String, token: Int): LiveData<Resource<AuthResponse>>{
        val mutableLiveData = MutableLiveData<Resource<AuthResponse>>()

        val otpRequest = HashMap<String, Any>()
        otpRequest["email"] = email
        otpRequest["token"] = token

        apiService.sendOTPCodeForgotPass(otpRequest).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        mutableLiveData.value = Resource.Success(body)
                    }
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

    fun resendOTPResetPassword(email: String): LiveData<Resource<AuthResponse>>{
        val mutableLiveData = MutableLiveData<Resource<AuthResponse>>()

        val otpRequest = HashMap<String, String>()
        otpRequest["email"] = email

        apiService.resendEmailForgotPass(otpRequest).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        mutableLiveData.value = Resource.Success(body)
                    }
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