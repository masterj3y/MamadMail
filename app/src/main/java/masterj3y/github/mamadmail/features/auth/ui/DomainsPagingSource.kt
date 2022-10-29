package masterj3y.github.mamadmail.features.auth.ui

import dagger.hilt.android.scopes.ViewModelScoped
import masterj3y.github.mamadmail.common.paging.BasicPagingSource
import masterj3y.github.mamadmail.features.auth.data.DomainsRepository
import masterj3y.github.mamadmail.features.auth.model.Domain
import javax.inject.Inject

@ViewModelScoped
class DomainsPagingSource @Inject constructor(repository: DomainsRepository) :
    BasicPagingSource<Domain>(
        request = { page -> repository.fetchDomains(page) }
    )