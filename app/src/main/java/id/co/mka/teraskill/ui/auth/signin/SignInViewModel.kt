package id.co.mka.teraskill.ui.auth.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.UserInfo
import id.co.mka.teraskill.data.responses.AuthResponse
import id.co.mka.teraskill.di.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel: ViewModel() {

    fun loginUser(bodyRequest: UserInfo): LiveData<AuthResponse?> {

        val mutableLiveData = MutableLiveData<AuthResponse?>()

        ApiConfig.getApiService().loginUser(bodyRequest).enqueue(object : Callback<AuthResponse> {

            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                if (response.isSuccessful) {
                    mutableLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("LoginViewModel", "onFailure: $t")
                mutableLiveData.value = null
            }
        })
        return mutableLiveData
    }
}