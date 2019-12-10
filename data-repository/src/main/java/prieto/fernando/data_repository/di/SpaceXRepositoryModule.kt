package prieto.fernando.data_repository.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import prieto.fernando.data_repository.SpaceXRemoteSource
import prieto.fernando.data_repository.mapper.CompanyInfoRepositoryToDomainModelMapper
import prieto.fernando.data_repository.mapper.CompanyInfoRepositoryToDomainModelMapperImpl
import prieto.fernando.data_repository.mapper.LaunchesRepositoryToDomainModelMapper
import prieto.fernando.data_repository.mapper.LaunchesRepositoryToDomainModelMapperImpl
import prieto.fernando.data_repository.repository.SpaceXRepositoryImpl
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
