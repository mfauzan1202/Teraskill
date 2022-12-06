package id.co.mka.teraskill.ui.classroom.class_theory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.ModulResponse
import id.co.mka.teraskill.data.responses.UpdateProgressResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassSubChapterViewModel(private val apiService: ApiService) : ViewModel() {

    fun getSubChapter(uuid: String): LiveData<Resource<List<ModulResponse>?>> {
        val mutableLiveData = MutableLiveData<Resource<List<ModulResponse>?>>()

        apiService.getModulByID(uuid)
            .enqueue(object : Callback<List<ModulResponse>> {
                override fun onResponse(
                    call: Call<List<ModulResponse>>,
                    response: Response<List<ModulResponse>>
                ) {
                    if (response.isSuccessful) {
                        mutableLiveData.value = Resource.Success(response.body())
                    }
                    else {
                        mutableLiveData.value =
                            Resource.Error(null, "Gagal memuat data", response.code())
                    }
                }

                override fun onFailure(call: Call<List<ModulResponse>>, t: Throwable) {
                    when (t) {
                        is java.net.SocketTimeoutException -> {
                            mutableLiveData.value = Resource.Error(null, "Koneksi Bermasalah", 0)
                        }
                        is java.net.UnknownHostException -> {
                            mutableLiveData.value = Resource.Error(null, "", 1)
                        }
                        else -> {

                        }
                    }
                }
            })
        return mutableLiveData
    }

    fun updateProgress(uuid: String): LiveData<Resource<UpdateProgressResponse.Response>> {
        val mutableLiveData = MutableLiveData<Resource<UpdateProgressResponse.Response>>()

        apiService.updateStatusChapter(uuid)
            .enqueue(object : Callback<UpdateProgressResponse> {
                override fun onResponse(
                    call: Call<UpdateProgressResponse>,
                    response: Response<UpdateProgressResponse>
                ) {
                    if (response.isSuccessful) {
                        mutableLiveData.value = Resource.Success(response.body()!!.response)
                    }
                    else {
                        mutableLiveData.value =
                            Resource.Error(null, "Gagal memuat data", response.code())
                    }
                }

                override fun onFailure(call: Call<UpdateProgressResponse>, t: Throwable) {
                    when (t) {
                        is java.net.SocketTimeoutException -> {
                            mutableLiveData.value = Resource.Error(null, "Koneksi Bermasalah", 0)
                        }
                        is java.net.UnknownHostException -> {
                            mutableLiveData.value = Resource.Error(null, "", 1)
                        }
                        else -> {

                        }
                    }
                }
            })
        return mutableLiveData
    }
}