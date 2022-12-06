package id.co.mka.teraskill.ui.learning_path

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.co.mka.teraskill.data.repository.ClassRepository
import id.co.mka.teraskill.data.responses.AllClassResponse
import id.co.mka.teraskill.data.responses.LearningPathResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LearningPathViewModel(
    private val apiService: ApiService,
    private val classRepository: ClassRepository
) :
    ViewModel() {

    private val _classInfo = classRepository.getClass().cachedIn(viewModelScope)
    val classInfo: LiveData<PagingData<AllClassResponse>> = _classInfo


    fun classInfoByLearningPath(uuid: String): LiveData<PagingData<AllClassResponse>> =
        classRepository.getClassByLearningPath(uuid).cachedIn(viewModelScope)

    fun getLearningPath(): LiveData<Resource<List<LearningPathResponse>>> {
        val mutableLiveData = MutableLiveData<Resource<List<LearningPathResponse>>>()

        apiService.getLearningPath().enqueue(object : Callback<List<LearningPathResponse>> {
            override fun onResponse(
                call: Call<List<LearningPathResponse>>,
                response: Response<List<LearningPathResponse>>
            ) {
                if (response.isSuccessful) {
                    mutableLiveData.value = Resource.Success(response.body()!!)
                } else {
                    mutableLiveData.value =
                        Resource.Error(null, "Gagal Memuat data Learning Path", response.code())
                }
            }

            override fun onFailure(call: Call<List<LearningPathResponse>>, t: Throwable) {
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