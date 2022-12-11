package id.co.mka.teraskill.ui.main.home.webinar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.WebinarResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebinarViewModel(private val apiService: ApiService) : ViewModel() {

    fun getWebinar(): LiveData<Resource<List<WebinarResponse>>> {
        val mutableLiveData = MutableLiveData<Resource<List<WebinarResponse>>>()
        apiService.getWebinar().enqueue(object : Callback<List<WebinarResponse>> {
            override fun onResponse(
                call: Call<List<WebinarResponse>>,
                response: Response<List<WebinarResponse>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        mutableLiveData.value = Resource.Success(body)
                    }
                } else {
                    mutableLiveData.value =
                        Resource.Error(null, response.message(), response.code())
                }
            }

            override fun onFailure(call: Call<List<WebinarResponse>>, t: Throwable) {
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