package id.co.mka.teraskill.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.co.mka.teraskill.data.responses.AllClassResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Constants

class ClassByLearningPathPagingSource(private val apiService: ApiService, private val uuid: String): PagingSource<Int, AllClassResponse>() {

    override fun getRefreshKey(state: PagingState<Int, AllClassResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AllClassResponse> {
        return try {
            val position = params.key ?: Constants.INITIAL_PAGE_INDEX
            val responseData = apiService.getClassByLearningPath(uuid, params.loadSize, position).result
            LoadResult.Page(
                data = responseData,
                prevKey = if (position == Constants.INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}