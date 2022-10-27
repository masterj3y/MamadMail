package masterj3y.github.mamadmail.features.auth.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import masterj3y.github.mamadmail.features.auth.data.*
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthModule {

    @Binds
    @ViewModelScoped
    abstract fun bindDomainRepository(domainsRepositoryImpl: DomainsRepositoryImpl): DomainsRepository

    @Binds
    @ViewModelScoped
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    companion object {

        @Provides
        @ViewModelScoped
        fun provideDomainsService(retrofit: Retrofit): DomainsService =
            retrofit.create(DomainsService::class.java)

        @Provides
        @ViewModelScoped
        fun provideAuthService(retrofit: Retrofit): AuthService =
            retrofit.create(AuthService::class.java)
    }
}