package masterj3y.github.mamadmail.di

import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import masterj3y.github.mamadmail.common.session.UserSessionManager
import masterj3y.github.mamadmail.features.auth.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val BASE_URL = "https://api.mail.gw/"

    @Provides
    @Singleton
    fun provideRetrofit(userSessionManager: UserSessionManager): Retrofit {

        val logging = HttpLoggingInterceptor { message ->
            Timber.tag("network").d(message)
        }.apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val httpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(logging)
            .addInterceptor(AuthInterceptor(userSessionManager))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}