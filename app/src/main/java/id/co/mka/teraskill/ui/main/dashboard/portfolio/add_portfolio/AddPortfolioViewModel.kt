package id.co.mka.teraskill.ui.main.dashboard.portfolio.add_portfolio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.MessageResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import id.co.mka.teraskill.utils.toMultipartBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddPortfolioViewModel(private val apiService: ApiService) : ViewModel() {

    fun addPortfolio(
        project_name: String,
        position: String,
        selfDesc: String,
        projectDesc: String,
        link: String,
        image: File
    ): LiveData<Resource<MessageResponse>> {
        val mutableLiveData = MutableLiveData<Resource<MessageResponse>>()

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("project_name", project_name)
            .addFormDataPart("posisi", position)
            .addFormDataPart("deskripsi_diri", selfDesc)
            .addFormDataPart("deskripsi_project", projectDesc)
            .addFormDataPart("link", link)
            .addPart(image.toMultipartBody("image"))
            .build()

        apiService.addPortfolio(requestBody).enqueue(object : Callback<MessageResponse> {
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
                    mutableLiveData.value =
                        Resource.Error(null, response.message(), response.code())
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