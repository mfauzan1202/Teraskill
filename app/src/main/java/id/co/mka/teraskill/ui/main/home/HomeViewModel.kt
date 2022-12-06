package id.co.mka.teraskill.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.co.mka.teraskill.data.repository.ClassRepository
import id.co.mka.teraskill.data.responses.AllClassResponse
import id.co.mka.teraskill.data.responses.LearningPathResponse
import id.co.mka.teraskill.data.services.ApiConfig
import id.co.mka.teraskill.data.services.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val classRepository: ClassRepository): ViewModel() {

    private val _classInfo = classRepository.getClass().cachedIn(viewModelScope)
    val classInfo: LiveData<PagingData<AllClassResponse>> = _classInfo

    private val _learningPath = MutableLiveData<List<LearningPathResponse>>()
    val learningPath: LiveData<List<LearningPathResponse>> = _learningPath

    fun getLearningPath() {
        viewModelScope.launch {
            _learningPath.postValue(classRepository.getLearningPath())
        }
    }
}