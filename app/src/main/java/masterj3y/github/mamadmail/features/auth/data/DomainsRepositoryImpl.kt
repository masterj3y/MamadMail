package masterj3y.github.mamadmail.features.auth.data

import com.skydoves.sandwich.ApiResponse
import masterj3y.github.mamadmail.common.model.PagingResponse
import masterj3y.github.mamadmail.features.auth.model.Domain
import javax.inject.Inject

class DomainsRepositoryImpl @Inject constructor(private val service: DomainsService) :
    DomainsRepository {

    override suspend fun fetchDomains(page: Int): ApiResponse<PagingResponse<Domain>> =
        service.fetchDomains(page)
}