package prieto.fernando.data.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import prieto.fernando.data.SpaceXRemoteSource
import prieto.fernando.data.mapper.CompanyInfoRepositoryToDomainModelMapper
import prieto.fernando.data.mapper.CompanyInfoRepositoryToDomainModelMapperImpl
import prieto.fernando.data.mapper.LaunchesRepositoryToDomainModelMapper
import prieto.fernando.data.mapper.LaunchesRepositoryToDomainModelMapperImpl
import prieto.fernando.data.repository.CompanyInfoRepositoryImpl
import prieto.fernando.data.repository.LaunchesRepositoryImpl
import prieto.fernando.feature.dashboard.domain.repository.CompanyInfoRepository
import prieto.fernando.feature.launches.domain.repository.LaunchesRepository

@Module
@InstallIn(SingletonComponent::class)
object SpaceXRepositoryModule {
    
    @Provides
    @Reusable
    fun provideCompanyInfoRepository(
        spaceXRemoteSource: SpaceXRemoteSource,
        companyInfoRepositoryToDomainModelMapper: CompanyInfoRepositoryToDomainModelMapper
    ): CompanyInfoRepository = CompanyInfoRepositoryImpl(
        spaceXRemoteSource,
        companyInfoRepositoryToDomainModelMapper
    )

    @Provides
    @Reusable
    fun provideLaunchesRepository(
        spaceXRemoteSource: SpaceXRemoteSource,
        launchesRepositoryToDomainModelMapper: LaunchesRepositoryToDomainModelMapper
    ): LaunchesRepository = LaunchesRepositoryImpl(
        spaceXRemoteSource,
        launchesRepositoryToDomainModelMapper
    )

    @Provides
    @Reusable
    fun provideCompanyInfoRepositoryToDomainModelMapper(): CompanyInfoRepositoryToDomainModelMapper =
        CompanyInfoRepositoryToDomainModelMapperImpl()

    @Provides
    @Reusable
    fun provideLaunchesRepositoryToDomainModelMapper(): LaunchesRepositoryToDomainModelMapper =
        LaunchesRepositoryToDomainModelMapperImpl()
}
