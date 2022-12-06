package id.co.mka.teraskill.ui.main.dashboard.class_progress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.ClassProgressResponse
import id.co.mka.teraskill.data.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassProgressViewModel(private val apiService: ApiService): ViewModel() {

    fun getProgress(): LiveData<ClassProgressResponse?> {
        val mutableLiveData = MutableLiveData<ClassProgressResponse?>()

        apiService.getProgress().enqueue(object : Callback<ClassProgressResponse> {
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