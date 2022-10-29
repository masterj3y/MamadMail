package masterj3y.github.mamadmail.features.profile.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import masterj3y.github.mamadmail.features.profile.data.ProfileRepository
import masterj3y.github.mamadmail.features.profile.data.ProfileRepositoryImpl
import masterj3y.github.mamadmail.features.profile.data.ProfileService
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class ProfileModule {

    @Binds
    @ViewModelScoped
    abstract fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

    companion object {

        @Provides
        @ViewModelScoped
        fun provideProfileService(retrofit: Retrofit): ProfileService =
            retrofit.create(ProfileService::class.java)
    }
}