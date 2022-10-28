package masterj3y.github.mamadmail.features.inbox.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import masterj3y.github.mamadmail.features.inbox.data.MessageRepository
import masterj3y.github.mamadmail.features.inbox.data.MessageRepositoryImpl
import masterj3y.github.mamadmail.features.inbox.data.MessageService
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class InboxModule {

    @Binds
    @ViewModelScoped
    abstract fun bindMessageRepository(messageRepositoryImpl: MessageRepositoryImpl): MessageRepository

    companion object {

        @Provides
        @ViewModelScoped
        fun provideMessageService(retrofit: Retrofit): MessageService =
            retrofit.create(MessageService::class.java)
    }
}