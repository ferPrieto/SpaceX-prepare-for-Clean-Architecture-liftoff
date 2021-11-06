package prieto.fernando.data_api.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import prieto.fernando.data.SpaceXRemoteSource
import prieto.fernando.data_api.ApiService
import prieto.fernando.data_api.data.SpaceXRemoteSourceImpl
import prieto.fernando.data_api.mapper.*
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideSpaceXRemoteSource(
        apiService: ApiService,
        companyInfoRepositoryMapper: CompanyInfoResponseToRepositoryModelMapper,
        launchesRepositoryMapper: LaunchesResponseToRepositoryModelMapper
    ): SpaceXRemoteSource =
        SpaceXRemoteSourceImpl(apiService, companyInfoRepositoryMapper, launchesRepositoryMapper)

    @Provides
    fun provideCompanyInfoResponseToRepositoryModelMapper(): CompanyInfoResponseToRepositoryModelMapper =
        CompanyInfoResponseToRepositoryModelMapperImpl()

    @Provides
    fun provideLaunchesResponseToRepositoryModelMapper(
        dateFormatter: DateFormatter
    ): LaunchesResponseToRepositoryModelMapper =
        LaunchesResponseToRepositoryModelMapperImpl(dateFormatter)

    @Provides
    fun provideDateFormatter(): DateFormatter =
        DateFormatterImpl()

    @Provides
    @JvmStatic
    internal fun provideApi(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @JvmStatic
    internal fun provideRetrofit(
        httpBuilder: OkHttpClient.Builder,
        retrofitBuilder: Retrofit.Builder
    ): Retrofit = retrofitBuilder
        .client(httpBuilder.build())
        .build()
}