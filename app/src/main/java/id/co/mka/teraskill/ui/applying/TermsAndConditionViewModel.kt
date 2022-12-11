package id.co.mka.teraskill.ui.applying

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.StatusApplyResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermsAndConditionViewModel(private val apiService: ApiService): ViewModel() {

    fun getTermsAndCondition(): LiveData<Resource<List<StatusApplyResponse>>> {
        val mutableLiveData = MutableLiveData<Resource<List<StatusApplyResponse>>>()

        apiService.getStatusApply().enqueue(object : Callback<List<StatusApplyResponse>> {
            override fun onResponse(
                call: Call<List<StatusApplyResponse>>,
                response: Response<List<StatusApplyResponse>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        mutableLiveData.value = Resource.Success(body)
                    }
                }
            }

            override fun onFailure(call: Call<List<StatusApplyResponse>>, t: Throwable) {
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