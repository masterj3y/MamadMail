package masterj3y.github.mamadmail.features.auth.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skydoves.sandwich.getOrNull
import dagger.hilt.android.scopes.ViewModelScoped
import masterj3y.github.mamadmail.features.auth.data.DomainsRepository
import masterj3y.github.mamadmail.features.auth.model.Domain
import java.io.IOException
import javax.inject.Inject

@ViewModelScoped
class DomainsPagingSource @Inject constructor(private val repository: DomainsRepository) :
    PagingSource<Int, Domain>() {

    private var itemsPerPage: Int? = null

    override fun getRefreshKey(state: PagingState<Int, Domain>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Domain> {
        val nextPage: Int = params.key ?: FIRST_PAGE

        return try {

            val response = repository.fetchDomains(nextPage).getOrNull()

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
                    prevKey = if (nextPage == FIRST_PAGE) null else nextPage - 1,
                    nextKey = if (domains.isEmpty() || domains.size < (itemsPerPage
                            ?: 0)
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