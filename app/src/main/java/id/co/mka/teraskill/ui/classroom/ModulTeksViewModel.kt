package id.co.mka.teraskill.ui.classroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.mka.teraskill.data.responses.ClassResponseItem
import id.co.mka.teraskill.di.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModulTeksViewModel : ViewModel() {

    fun getDetailClass(token: String, id: String): LiveData<ClassResponseItem?> {
        val mutableLiveData = MutableLiveData<ClassResponseItem?>()

        ApiConfig.getApiService(token).getClassInfoByID(id).enqueue(object :
            Callback<ClassResponseItem> {
            override fun onResponse(
                call: Call<ClassResponseItem>,
                response: Response<ClassResponseItem>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    mutableLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<ClassResponseItem>, t: Throwable) {
                mutableLiveData.value = null
            }
        })
        return mutableLiveData
    }

}