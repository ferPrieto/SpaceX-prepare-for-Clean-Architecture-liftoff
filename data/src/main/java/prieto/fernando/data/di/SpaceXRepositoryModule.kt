package prieto.fernando.data.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import prieto.fernando.data.SpaceXRemoteSource
import prieto.fernando.data.mapper.CompanyInfoRepositoryToDomainModelMapper
import prieto.fernando.data.mapper.CompanyInfoRepositoryToDomainModelMapperImpl
import prieto.fernando.data.mapper.LaunchesRepositoryToDomainModelMapper
import prieto.fernando.data.mapper.LaunchesRepositoryToDomainModelMapperImpl
import prieto.fernando.data.repository.SpaceXRepositoryImpl
import prieto.fernando.domain.SpaceXRepository

@Module
class SpaceXRepositoryModule {

    @Provides
    @Reusable
    fun provideSpaceXRepository(
        spaceXRemoteSource: SpaceXRemoteSource,
        companyInfoRepositoryToDomainModelMapper: CompanyInfoRepositoryToDomainModelMapper,
        launchesRepositoryToDomainModelMapper: LaunchesRepositoryToDomainModelMapper
    ): SpaceXRepository = SpaceXRepositoryImpl(
        spaceXRemoteSource,
        companyInfoRepositoryToDomainModelMapper,
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
