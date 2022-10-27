package masterj3y.github.mamadmail.features.auth.data

import com.skydoves.sandwich.ApiResponse
import masterj3y.github.mamadmail.common.model.PagingResponse
import masterj3y.github.mamadmail.features.auth.model.Domain

interface DomainsRepository {

    suspend fun fetchDomains(page: Int): ApiResponse<PagingResponse<Domain>>
}