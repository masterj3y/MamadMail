package masterj3y.github.mamadmail.common.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.getOrNull
import masterj3y.github.mamadmail.common.model.PagingResponse
import java.io.IOException

abstract class BasicPagingSource<T : Any>(private val request: suspend (page: Int) -> ApiResponse<PagingResponse<T>>) :
    PagingSource<Int, T>() {

    private var itemsPerPage: Int? = null

    override fun getRefreshKey(state: PagingState<Int, T>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val nextPage: Int = params.key?.let {
            if (it < FIRST_PAGE) FIRST_PAGE else it
        } ?: FIRST_PAGE

        return try {

            val response = request(nextPage).getOrNull()

            val domains = response?.items
            val currentPageItemsSize = domains?.size

            if (itemsPerPage == null && nextPage == FIRST_PAGE)
                itemsPerPage = when {
                    (currentPageItemsSize ?: 0) < (response?.itemsCount
                        ?: 0) -> currentPageItemsSize
                    else -> null
                }

            if (domains != null) {
                LoadResult.Page(
                    data = domains,
                    prevKey = when {
                        nextPage > FIRST_PAGE -> nextPage - 1
                        else -> null
                    },
                    nextKey = if (domains.isEmpty() || domains.size < (itemsPerPage ?: 0)
                    ) null else nextPage + 1
                )
            } else {
                LoadResult.Error(Throwable("Unknown error occurred while fetching pages"))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}