package id.co.mka.teraskill.ui.applying

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.AuthResponse
import id.co.mka.teraskill.di.ApiConfig
import id.co.mka.teraskill.utils.toMultipartBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ApplyingViewModel: ViewModel() {

    fun uploadFile(token: String,job: String, file1: File, file2: File): LiveData<AuthResponse?> {

        val mutableLiveData = MutableLiveData<AuthResponse?>()

        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addPart(file1.toMultipartBody("surat_pernyataan"))
            .addPart(file2.toMultipartBody("portofolio"))
            .addFormDataPart("job", job)
            .build()

        ApiConfig.getApiService(token).uploadFile(body).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    mutableLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                mutableLiveData.value = null
                Log.d("FAIL", "onFailure: " + t.message)
            }
        })
        return mutableLiveData
    }
}