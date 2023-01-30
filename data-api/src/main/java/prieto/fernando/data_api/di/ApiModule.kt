package prieto.fernando.data_api.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import prieto.fernando.data.SpaceXRemoteSource
import prieto.fernando.data_api.ApiService
import prieto.fernando.data_api.data.SpaceXRemoteSourceImpl
import prieto.fernando.data_api.mapper.CompanyInfoResponseToRepositoryModelMapper
import prieto.fernando.data_api.mapper.CompanyInfoResponseToRepositoryModelMapperImpl
import prieto.fernando.data_api.mapper.DateFormatter
import prieto.fernando.data_api.mapper.DateFormatterImpl
import prieto.fernando.data_api.mapper.LaunchesResponseToRepositoryModelMapper
import prieto.fernando.data_api.mapper.LaunchesResponseToRepositoryModelMapperImpl
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
    @Reusable
    fun provideCompanyInfoResponseToRepositoryModelMapper(): CompanyInfoResponseToRepositoryModelMapper =
        CompanyInfoResponseToRepositoryModelMapperImpl()

    @Provides
    @Reusable
    fun provideLaunchesResponseToRepositoryModelMapper(
        dateFormatter: DateFormatter
    ): LaunchesResponseToRepositoryModelMapper =
        LaunchesResponseToRepositoryModelMapperImpl(dateFormatter)

    @Provides
    @Reusable
    fun provideDateFormatter(): DateFormatter =
        DateFormatterImpl()

    @Provides
    @JvmStatic
    internal fun provideApi(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @JvmStatic
    @Reusable
    internal fun provideRetrofit(
        httpBuilder: OkHttpClient.Builder,
        retrofitBuilder: Retrofit.Builder
    ): Retrofit = retrofitBuilder
        .client(httpBuilder.build())
        .build()
}