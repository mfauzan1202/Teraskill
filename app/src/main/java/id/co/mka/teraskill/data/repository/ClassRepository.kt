package id.co.mka.teraskill.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import id.co.mka.teraskill.data.paging_source.ClassByLearningPathPagingSource
import id.co.mka.teraskill.data.paging_source.ClassPagingSource
import id.co.mka.teraskill.data.responses.AllClassResponse
import id.co.mka.teraskill.data.responses.LearningPathResponse
import id.co.mka.teraskill.data.services.ApiService

class ClassRepository(private val apiService: ApiService) {

    fun getClass(): LiveData<PagingData<AllClassResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 4,
                initialLoadSize = 4
            ),
            pagingSourceFactory = {
                ClassPagingSource(apiService)
            }
        ).liveData
    }

    fun getClassByLearningPath(uuid: String): LiveData<PagingData<AllClassResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 4,
                initialLoadSize = 4
            ),
            pagingSourceFactory = {
                ClassByLearningPathPagingSource(apiService, uuid)
            }
        ).liveData
    }

    suspend fun getLearningPath(): List<LearningPathResponse> {
        return apiService.getAllLearningPath()
    }
}