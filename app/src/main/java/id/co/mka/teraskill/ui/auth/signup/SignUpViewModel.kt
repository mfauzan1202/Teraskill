package id.co.mka.teraskill.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.UserInfo
import id.co.mka.teraskill.data.responses.AuthResponse
import id.co.mka.teraskill.di.ApiConfig
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel: ViewModel() {

    fun signUpUser(body: UserInfo): LiveData<AuthResponse?> {
        val mutableLiveData = MutableLiveData<AuthResponse?>()

        ApiConfig.getApiService().registerUser(body).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    mutableLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                mutableLiveData.value = null
            }
        })
        return mutableLiveData
    }
}