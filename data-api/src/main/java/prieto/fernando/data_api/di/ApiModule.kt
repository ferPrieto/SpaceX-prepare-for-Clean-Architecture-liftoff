package prieto.fernando.data_api.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import prieto.fernando.data_api.ApiService
import prieto.fernando.data_api.data.SpaceXRemoteSourceImpl
import prieto.fernando.data_api.mapper.*
import prieto.fernando.data.SpaceXRemoteSource
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {
    @Singleton
    @Provides
    fun provideSpaceXRemoteSource(
        apiService: ApiService,
        companyInfoRepositoryMapper: CompanyInfoResponseToRepositoryModelMapper,
        launchesRepositoryMapper: LaunchesResponseToRepositoryModelMapper
    ): SpaceXRemoteSource =
        SpaceXRemoteSourceImpl(apiService, companyInfoRepositoryMapper, launchesRepositoryMapper)

    @Reusable
    @Provides
    fun provideCompanyInfoResponseToRepositoryModelMapper(): CompanyInfoResponseToRepositoryModelMapper =
        CompanyInfoResponseToRepositoryModelMapperImpl()

    @Reusable
    @Provides
    fun provideLaunchesResponseToRepositoryModelMapper(
        dateFormatter: DateFormatter
    ): LaunchesResponseToRepositoryModelMapper =
        LaunchesResponseToRepositoryModelMapperImpl(dateFormatter)

    @Provides
    @Reusable
    fun provideDateFormatter(): DateFormatter =
        DateFormatterImpl()

    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        internal fun provideApi(retrofit: Retrofit): ApiService =
            retrofit.create(ApiService::class.java)

        @Provides
        @JvmStatic
        @Singleton
        internal fun provideRetrofit(
            httpBuilder: OkHttpClient.Builder,
            retrofitBuilder: Retrofit.Builder
        ): Retrofit = retrofitBuilder
            .client(httpBuilder.build())
            .build()
    }
}