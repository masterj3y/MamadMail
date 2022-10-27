package masterj3y.github.mamadmail.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import masterj3y.github.mamadmail.common.session.UserSessionManager
import masterj3y.github.mamadmail.common.session.UserSessionManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {

    @Binds
    @Singleton
    abstract fun bindUserSessionManager(userSessionManagerImpl: UserSessionManagerImpl): UserSessionManager
}