package id.co.mka.teraskill.ui.main.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.ClassProgressResponse
import id.co.mka.teraskill.di.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassProgressViewModel: ViewModel() {

    fun getProgress(token: String): LiveData<ClassProgressResponse?> {
        val mutableLiveData = MutableLiveData<ClassProgressResponse?>()

        ApiConfig.getApiService(token).getProgress().enqueue(object : Callback<ClassProgressResponse> {
            override fun onResponse(call: Call<ClassProgressResponse>, response: Response<ClassProgressResponse>) {
                if (response.isSuccessful) {
                    mutableLiveData.value = response.body()
                }
                else{
                    mutableLiveData.value = null
                }
            }

            override fun onFailure(call: Call<ClassProgressResponse>, t: Throwable) {
                mutableLiveData.value = null
            }
        })
        return mutableLiveData
    }
}