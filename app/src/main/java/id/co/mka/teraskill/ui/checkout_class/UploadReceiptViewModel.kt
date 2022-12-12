package id.co.mka.teraskill.ui.checkout_class

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.MyClassResponse
import id.co.mka.teraskill.data.responses.SingleClassResponse
import id.co.mka.teraskill.data.services.ApiConfig
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Resource
import id.co.mka.teraskill.utils.toMultipartBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadReceiptViewModel(private val apiService: ApiService): ViewModel() {

    fun joinClass(id: Int): LiveData<Resource<SingleClassResponse?>>{
        val mutableLiveData = MutableLiveData<Resource<SingleClassResponse?>>()
        val classId = HashMap<String, Int>()
        classId["kelasId"] = id
        apiService.buyClass(classId).enqueue(object :
            Callback<SingleClassResponse> {
            override fun onResponse(
                call: Call<SingleClassResponse>,
                response: Response<SingleClassResponse>
            ) {
                if (response.isSuccessful) {
                    mutableLiveData.value = Resource.Success(response.body())
                } else {
                    mutableLiveData.value = Resource.Error(null, "Gagal Bergabung", response.code())
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

    fun uploadReceipt(token: String, file: File, noRef: String, uuid: String): LiveData<String> {
        val mutableLiveData = MutableLiveData<String>()

        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addPart(file.toMultipartBody("bukti_pembayaran"))
            .addFormDataPart("no_ref", noRef)
            .build()

        ApiConfig.getApiService().getAllUserClass().enqueue(object : Callback<List<MyClassResponse>> {
            override fun onResponse(
                call: Call<List<MyClassResponse>>,
                response: Response<List<MyClassResponse>>
            ) {
                if (response.isSuccessful) {
                    response.body()!!.forEach { myClass ->
                        if (myClass.kelasTeraskill.uuid == uuid) {
                            ApiConfig.getApiService().uploadReceiptBuyClass(myClass.uuid, body).enqueue(object : Callback<SingleClassResponse> {
                                override fun onResponse(
                                    call: Call<SingleClassResponse>,
                                    response: Response<SingleClassResponse>
                                ) {
                                    if (response.isSuccessful) {
                                        mutableLiveData.value = "Berhasil mengirim bukti pembayaran"
                                    } else {
                                        mutableLiveData.value = "Gagal mengirim bukti pembayaran"
                                    }
                                }

                                override fun onFailure(call: Call<SingleClassResponse>, t: Throwable) {
                                    when (t) {
                                        is java.net.SocketTimeoutException -> {
                                            mutableLiveData.value = "Koneksi Bermasalah"
                                        }
                                        is java.net.UnknownHostException -> {
                                            mutableLiveData.value = "Koneksi Bermasalah"
                                        }
                                        is java.net.ConnectException -> {
                                            mutableLiveData.value = "Koneksi Bermasalah"
                                        }
                                        else -> {
                                            mutableLiveData.value = "Koneksi Bermasalah"
                                        }
                                    }
                                }
                            })
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<MyClassResponse>>, t: Throwable) {
                when (t) {
                    is java.net.SocketTimeoutException -> {
                        mutableLiveData.value = "Koneksi Bermasalah"
                    }
                    is java.net.UnknownHostException -> {
                        mutableLiveData.value = "Koneksi Bermasalah"
                    }
                    is java.net.ConnectException -> {
                        mutableLiveData.value = "Koneksi Bermasalah"
                    }
                    else -> {
                        mutableLiveData.value = "Koneksi Bermasalah"
                    }
                }
            }
        })
        return mutableLiveData
    }
}