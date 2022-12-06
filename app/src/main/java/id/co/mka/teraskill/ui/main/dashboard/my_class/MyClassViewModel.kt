package id.co.mka.teraskill.ui.main.dashboard.my_class

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.MyClassResponse
import id.co.mka.teraskill.data.services.ApiConfig
import id.co.mka.teraskill.data.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyClassViewModel(private val apiService: ApiService) : ViewModel() {

    fun getUserClass(): LiveData<List<MyClassResponse>?> {
        val mutableLiveData = MutableLiveData<List<MyClassResponse>?>()

        apiService.getAllUserClass().enqueue(object : Callback<List<MyClassResponse>> {
            override fun onResponse(
                call: Call<List<MyClassResponse>>,
                response: Response<List<MyClassResponse>>
            ) {
                if (response.isSuccessful) {
                    mutableLiveData.value = response.body()
                } else {
                    mutableLiveData.value = null
                }
            }

            override fun onFailure(call: Call<List<MyClassResponse>>, t: Throwable) {
                mutableLiveData.value = null
            }
        })
        return mutableLiveData
    }
}