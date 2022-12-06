package id.co.mka.teraskill.ui.classroom.class_project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.AuthResponse
import id.co.mka.teraskill.data.responses.ProjectResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassProjectViewModel(private val apiService: ApiService) : ViewModel() {

    fun getProject(uuid: String): LiveData<Resource<List<ProjectResponse>>> {
        val mutableLiveData = MutableLiveData<Resource<List<ProjectResponse>>>()

        apiService.getProjectClass(uuid).enqueue(object : Callback<List<ProjectResponse>> {
            override fun onResponse(
                call: Call<List<ProjectResponse>>,
                response: Response<List<ProjectResponse>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        mutableLiveData.value = Resource.Success(body)
                    }
                }
            }

            override fun onFailure(call: Call<List<ProjectResponse>>, t: Throwable) {
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

    fun uploadProjectLink(uuid: String, link: String): LiveData<Resource<AuthResponse>> {
        val mutableLiveData = MutableLiveData<Resource<AuthResponse>>()

        val requestBody = HashMap<String, String>()
        requestBody["link"] = link
        apiService.submitProject(uuid, requestBody).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        mutableLiveData.value = Resource.Success(body)
                    }
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
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