package id.co.mka.teraskill.ui.applying.upload_cv

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.dataclass.ApplyData
import id.co.mka.teraskill.data.responses.MessageResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.toMultipartBodyPdf
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadCVViewModel(private val apiService: ApiService): ViewModel() {

    fun uploadFile(file: File, data: ApplyData, skill: String): LiveData<MessageResponse?> {

        val mutableLiveData = MutableLiveData<MessageResponse?>()
        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addPart(file.toMultipartBodyPdf("sertifikat"))
            .addFormDataPart("pekerjaan", data.job)
            .addFormDataPart("tanggal_lahir", data.birthDate)
            .addFormDataPart("alamat", data.address)
            .addFormDataPart("instansi", data.agency)
            .addFormDataPart("nama_pemilik_rekening", data.accountName)
            .addFormDataPart("no_rekening", data.accountNumber)
            .addFormDataPart("bank", data.bankName)
            .addFormDataPart("keahlian", skill)
            .build()

        apiService.uploadFile(body).enqueue(object : Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                if (response.isSuccessful) {
                    mutableLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                mutableLiveData.value = null
                Log.d("FAIL", "onFailure: " + t.message)
            }
        })
        return mutableLiveData
    }
}