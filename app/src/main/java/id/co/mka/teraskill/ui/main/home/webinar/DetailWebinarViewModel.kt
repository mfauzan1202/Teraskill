package id.co.mka.teraskill.ui.main.home.webinar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.SingleWebinarResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailWebinarViewModel(private val apiService: ApiService): ViewModel() {

    fun getDetailWebinar(uuid: String): LiveData<Resource<SingleWebinarResponse>>{
        val mutableLiveData = MutableLiveData<Resource<SingleWebinarResponse>>()

        apiService.getWebinarUUID(uuid).enqueue(object : Callback<SingleWebinarResponse> {
            override fun onResponse(
                call: Call<SingleWebinarResponse>,
                response: Response<SingleWebinarResponse>
            ) {
                if (response.isSuccessful){
                    mutableLiveData.value = Resource.Success(response.body()!!)
                } else {
                    mutableLiveData.value =
                        Resource.Error(null, response.message(), response.code())
                }
            }

            override fun onFailure(call: Call<SingleWebinarResponse>, t: Throwable) {
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