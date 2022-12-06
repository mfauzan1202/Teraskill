package id.co.mka.teraskill.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.co.mka.teraskill.data.responses.AllClassResponse
import id.co.mka.teraskill.data.services.ApiService
import id.co.mka.teraskill.utils.Constants.Companion.INITIAL_PAGE_INDEX

class ClassPagingSource(private val apiService: ApiService): PagingSource<Int, AllClassResponse>() {

    override fun getRefreshKey(state: PagingState<Int, AllClassResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AllClassResponse> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAllClass(params.loadSize, position).result
            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}