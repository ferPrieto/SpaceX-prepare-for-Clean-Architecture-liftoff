package prieto.fernando.data_api.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val RETROFIT_TIMEOUT = 10L

@InstallIn(SingletonComponent::class)
@Module
open class NetworkModule {

    open fun getBaseUrl() = "https://api.spacexdata.com/v3/"

    @Provides
    @BaseUrl
    fun provideBaseUrl() = getBaseUrl()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        converterFactory: Converter.Factory,
        @BaseUrl baseUrl: String
    ): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)

    @Provides
    @Singleton
    fun provideHttpBuilder() = OkHttpClient.Builder().apply {
        val isDebug = System.getProperty("debug")?.toBoolean() ?: true
        if (isDebug) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(httpLoggingInterceptor)
        }

        readTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)
        connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)
    }

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    @Provides
    @Singleton
    fun provideKotlinSerializationConverterFactory(
        json: Json
    ): Converter.Factory = json.asConverterFactory("application/json".toMediaType())

}
