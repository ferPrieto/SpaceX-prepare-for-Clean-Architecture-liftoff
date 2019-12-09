package prieto.fernando.data_api.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import prieto.fernando.data_api.ApiService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        internal fun provideApi(retrofit: Retrofit) =
            retrofit.create(ApiService::class.java)

        @Provides
        @JvmStatic
        @Singleton
        internal fun provideRetrofit(
            httpBuilder: OkHttpClient.Builder,
            retrofitBuilder: Retrofit.Builder
        ) = retrofitBuilder
            .client(httpBuilder.build())
            .build()
    }
}