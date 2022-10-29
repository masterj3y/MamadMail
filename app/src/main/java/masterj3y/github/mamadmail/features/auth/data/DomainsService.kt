package masterj3y.github.mamadmail.features.auth.data

import com.skydoves.sandwich.ApiResponse
import masterj3y.github.mamadmail.common.model.PagingResponse
import masterj3y.github.mamadmail.features.auth.model.Domain
import retrofit2.http.GET
import retrofit2.http.Query

interface DomainsService {

    @GET("domains")
    suspend fun fetchDomains(@Query("page") page: Int): ApiResponse<PagingResponse<Domain>>
}