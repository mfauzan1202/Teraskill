package id.co.mka.teraskill.ui.main.dashboard.portfolio.list_portfolio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.PortfolioResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPortfolioViewModel(private val apiService: ApiService): ViewModel() {

    fun getListPortfolio(): LiveData<Resource<List<PortfolioResponse>>> {
        val mutableLiveData = MutableLiveData<Resource<List<PortfolioResponse>>>()

        apiService.getPortfolio().enqueue(object : Callback<List<PortfolioResponse>>{
            override fun onResponse(
                call: Call<List<PortfolioResponse>>,
                response: Response<List<PortfolioResponse>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        mutableLiveData.value = Resource.Success(body)
                    }
                } else {
                    mutableLiveData.value = Resource.Error(null, "Gagal mengambil data", response.code())
                }
            }

            override fun onFailure(call: Call<List<PortfolioResponse>>, t: Throwable) {
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