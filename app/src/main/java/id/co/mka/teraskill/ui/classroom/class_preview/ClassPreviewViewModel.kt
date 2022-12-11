package id.co.mka.teraskill.ui.classroom.class_preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.MessageResponse
import id.co.mka.teraskill.data.responses.SingleClassResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassPreviewViewModel(private val apiService: ApiService) : ViewModel() {

    fun getDetailClass(uuid: String): LiveData<SingleClassResponse?> {
        val mutableLiveData = MutableLiveData<SingleClassResponse?>()

        apiService.getClassInfoByID(uuid).enqueue(object :
            Callback<SingleClassResponse> {
            override fun onResponse(
                call: Call<SingleClassResponse>,
                response: Response<SingleClassResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    mutableLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<SingleClassResponse>, t: Throwable) {
                mutableLiveData.value = null
            }
        })
        return mutableLiveData
    }

    fun joinFreeClass(id: Int): LiveData<Resource<SingleClassResponse?>>{
        val mutableLiveData = MutableLiveData<Resource<SingleClassResponse?>>()

        val classId = HashMap<String, Int>()
        classId["kelasId"] = id

        apiService.buyClass(classId).enqueue(object : Callback<SingleClassResponse>{
            override fun onResponse(
                call: Call<SingleClassResponse>,
                response: Response<SingleClassResponse>
            ) {
                if (response.isSuccessful) {
                    mutableLiveData.value = Resource.Success(response.body())
                } else {
                    mutableLiveData.value = Resource.Error(null, "Gagal memuat data", response.code())
                }
            }

            override fun onFailure(call: Call<SingleClassResponse>, t: Throwable) {
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

    fun submitReview(id: String, star: Int, reviewContent: String): LiveData<Resource<MessageResponse>>{
        val mutableLiveData = MutableLiveData<Resource<MessageResponse>>()

        val review = HashMap<String, String>()
        review["star"] = star.toString()
        review["review"] = reviewContent

        apiService.submitReview(id, review).enqueue(object : Callback<MessageResponse>{
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        mutableLiveData.value = Resource.Success(body)
                    }
                } else {
                    mutableLiveData.value = Resource.Error(null, "Gagal memuat data", response.code())
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
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