package id.co.mka.teraskill.ui.classroom.class_exam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.ExamResponse
import id.co.mka.teraskill.data.responses.ResultExamResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassExamViewModel(val apiService: ApiService) : ViewModel() {

    fun getExam(uuid: String): LiveData<Resource<List<ExamResponse>>> {
        val mutableLiveData = MutableLiveData<Resource<List<ExamResponse>>>()

        apiService.getExamByClassID(uuid)
            .enqueue(object : Callback<List<ExamResponse>> {
                override fun onResponse(
                    call: Call<List<ExamResponse>>,
                    response: Response<List<ExamResponse>>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            mutableLiveData.value = Resource.Success(data)
                        }
                    }
                }

                override fun onFailure(call: Call<List<ExamResponse>>, t: Throwable) {
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

    fun submitExam(uuid: String, result: Int): LiveData<Resource<ResultExamResponse>> {
        val mutableLiveData = MutableLiveData<Resource<ResultExamResponse>>()

        val requestBody = HashMap<String, Int>()
        requestBody["correct_answer"] = result

        apiService.submitExam(uuid, requestBody)
            .enqueue(object : Callback<ResultExamResponse> {
                override fun onResponse(
                    call: Call<ResultExamResponse>,
                    response: Response<ResultExamResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            mutableLiveData.value = Resource.Success(data)
                        }
                    }
                }

                override fun onFailure(call: Call<ResultExamResponse>, t: Throwable) {
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