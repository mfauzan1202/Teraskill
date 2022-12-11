package id.co.mka.teraskill.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.dataclass.UserInfo
import id.co.mka.teraskill.data.responses.MessageResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import id.co.mka.teraskill.utils.toMultipartBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SignUpViewModel(private val apiService: ApiService) : ViewModel() {

    fun signUpUser(data: UserInfo, file: File?): LiveData<Resource<MessageResponse?>> {
        val mutableLiveData = MutableLiveData<Resource<MessageResponse?>>()
        val body: MultipartBody

        if (file != null) {
            body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(file.toMultipartBody("avatar", "image"))
                .addFormDataPart("name", data.name!!)
                .addFormDataPart("email", data.email)
                .addFormDataPart("password", data.password)
                .addFormDataPart("confpassword", data.confpassword!!)
                .addFormDataPart("no_hp", data.no_hp!!)
                .build()
        } else {
            body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", data.name!!)
                .addFormDataPart("email", data.email)
                .addFormDataPart("password", data.password)
                .addFormDataPart("confpassword", data.confpassword!!)
                .addFormDataPart("no_hp", data.no_hp!!)
                .build()
        }

        apiService.registerUser(body).enqueue(object : Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                if (response.isSuccessful) {
                    mutableLiveData.value = Resource.Success(response.body())
                } else {
                    mutableLiveData.value =
                        Resource.Error(null, response.message(), response.code())
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
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