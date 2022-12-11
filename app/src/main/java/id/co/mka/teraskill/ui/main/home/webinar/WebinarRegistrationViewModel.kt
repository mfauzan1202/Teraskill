package id.co.mka.teraskill.ui.main.home.webinar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.MessageResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebinarRegistrationViewModel(private val apiService: ApiService): ViewModel() {

    fun registerWebinar(id: Int, name: String, job: String, email: String, noHp: String) : LiveData<Resource<MessageResponse>>{
        val mutableLiveData = MutableLiveData<Resource<MessageResponse>>()

        val requestBody = HashMap<String, Any>()
        requestBody["webinarId"] = id
        requestBody["nama"] = name
        requestBody["pekerjaan"] = job
        requestBody["email"] = email
        requestBody["no_hp"] = noHp


        apiService.registerWebinar(requestBody).enqueue(object : Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        mutableLiveData.value = Resource.Success(body)
                    }
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