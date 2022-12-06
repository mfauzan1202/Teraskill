package id.co.mka.teraskill.ui.main.settings.transaction_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.TransactionHistoryResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionHistoryViewModel(private val apiService: ApiService) : ViewModel() {

    fun getTransactionHistory(): LiveData<Resource<TransactionHistoryResponse>> {

        val mutableLiveData = MutableLiveData<Resource<TransactionHistoryResponse>>()

        apiService.getHistoryTransaction().enqueue(object : Callback<TransactionHistoryResponse> {
            override fun onResponse(
                call: Call<TransactionHistoryResponse>,
                response: Response<TransactionHistoryResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()
                    if (body != null) {
                        mutableLiveData.postValue(Resource.Success(body))
                    }
                }
            }

            override fun onFailure(call: Call<TransactionHistoryResponse>, t: Throwable) {
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

    fun getTransactionHistoryByDate(
        startDate: String,
        endDate: String
    ): LiveData<Resource<TransactionHistoryResponse>> {
        val mutableLiveData = MutableLiveData<Resource<TransactionHistoryResponse>>()

        apiService.getHistoryTransactionByDate(startDate, endDate)
            .enqueue(object : Callback<TransactionHistoryResponse> {
                override fun onResponse(
                    call: Call<TransactionHistoryResponse>,
                    response: Response<TransactionHistoryResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val body = response.body()
                        if (body != null) {
                            mutableLiveData.postValue(Resource.Success(body))
                        }
                    }
                }

                override fun onFailure(call: Call<TransactionHistoryResponse>, t: Throwable) {
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