package id.co.mka.teraskill.ui.main.settings.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.dataclass.UserInfo
import id.co.mka.teraskill.data.responses.AuthResponse
import id.co.mka.teraskill.data.responses.MessageResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import id.co.mka.teraskill.utils.toMultipartBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class EditProfileViewModel(private val apiService: ApiService) : ViewModel() {

    fun updateProfile(
        uuid: String,
        data: UserInfo,
    ): LiveData<Resource<MessageResponse?>> {
        val mutableLiveData = MutableLiveData<Resource<MessageResponse?>>()

        apiService.updateUser(uuid, data)
            .enqueue(object : Callback<MessageResponse> {

                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    if (response.isSuccessful) {
                        mutableLiveData.value = Resource.Success(response.body())
                    } else {
                        mutableLiveData.value = Resource.Error(null, "Login gagal", response.code())
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

    fun updateProfileAvatar(
        uuid: String,
        file: File?
    ): LiveData<Resource<AuthResponse.UpdateAvatarResponse?>> {
        val mutableLiveData = MutableLiveData<Resource<AuthResponse.UpdateAvatarResponse?>>()

        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addPart(file!!.toMultipartBody("avatar", "image"))
            .build()

        apiService.updateUserAvatar(uuid, body)
            .enqueue(object : Callback<AuthResponse.UpdateAvatarResponse> {

                override fun onResponse(
                    call: Call<AuthResponse.UpdateAvatarResponse>,
                    response: Response<AuthResponse.UpdateAvatarResponse>
                ) {
                    if (response.isSuccessful) {
                        mutableLiveData.value = Resource.Success(response.body())
                    } else {
                        mutableLiveData.value =
                            Resource.Error(null, "Update profil gagal", response.code())
                    }
                }

                override fun onFailure(
                    call: Call<AuthResponse.UpdateAvatarResponse>,
                    t: Throwable
                ) {
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