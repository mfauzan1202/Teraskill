package id.co.mka.teraskill.ui.classroom.class_certificate

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.CertificateResponse
import id.co.mka.teraskill.data.services.ApiConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import kotlin.concurrent.thread

class CertificateViewModel(val fileDir: File) : ViewModel() {

    private var pdfFileName: File
    private var dirPath: String = "${fileDir}/cert/pdffiles"
    private var fileName: String
    var isFileReadyObserver = MutableLiveData<Boolean>()

    init {
        val dirFile = File(dirPath)
        if (!dirFile.exists()) {
            dirFile.mkdirs()
        }
        fileName = "DemoGraphs.pdf"
        val file = "${dirPath}/${fileName}"
        pdfFileName = File(file)
        if (pdfFileName.exists()) {
            pdfFileName.delete()
        }
    }

    fun getPdfFileUri(): File = pdfFileName

    fun getLinkPdf(token: String, uuid: String): LiveData<CertificateResponse> {
        val mutableLiveData = MutableLiveData<CertificateResponse>()

        ApiConfig.getApiService(token).getLinkPdfFile(uuid)
            .enqueue(object : Callback<CertificateResponse> {
                override fun onResponse(
                    call: Call<CertificateResponse>,
                    response: Response<CertificateResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        mutableLiveData.value = response.body()
                        if (body != null) {
                            downloadPdfFile(body.sertifikat)
                        }
                    }
                }

                override fun onFailure(call: Call<CertificateResponse>, t: Throwable) {
                    Log.e("ERROR", t.message.toString())
                }
            })
        return mutableLiveData
    }

    fun downloadPdfFile(pdfUrl: String) {
        thread {
            ApiConfig.getApiService().downloadPdfFile(pdfUrl)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        isFileReadyObserver.postValue(false)
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Log.e("====", "====response : " + response)
                        Log.e("====", "====response : " + response.isSuccessful)
                        if (response.isSuccessful) {
                            val result = response.body()?.byteStream()
                            result?.let {
                                writeToFile(it)
                            } ?: kotlin.run {
                                isFileReadyObserver.postValue(false)
                            }
                        } else
                            isFileReadyObserver.postValue(false)
                    }
                })
        }
    }

    private fun writeToFile(inputStream: InputStream) {
        try {
            val fileReader = ByteArray(4096)
            var fileSizeDownloaded = 0
            val fos: OutputStream = FileOutputStream(pdfFileName)
            do {
                val read = inputStream.read(fileReader)
                if (read != -1) {
                    fos.write(fileReader, 0, read)
                    fileSizeDownloaded += read
                }
            } while (read != -1)
            fos.flush()
            fos.close()
            isFileReadyObserver.postValue(true)
        } catch (e: IOException) {
            isFileReadyObserver.postValue(false)
        }
    }

    fun savePdfFileToStorage() {
        val file = File(dirPath, fileName)
        if (file.exists()) {
            file.delete()
        }
        pdfFileName.renameTo(file)
    }
}