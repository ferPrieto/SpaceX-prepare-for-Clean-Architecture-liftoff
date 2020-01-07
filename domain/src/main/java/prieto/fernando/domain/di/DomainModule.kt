package prieto.fernando.domain.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.mapper.*
import prieto.fernando.domain.usecase.GetCompanyInfo
import prieto.fernando.domain.usecase.GetCompanyInfoImpl
import prieto.fernando.domain.usecase.GetLaunches
import prieto.fernando.domain.usecase.GetLaunchesImpl

@Module
class DomainModule {
    @Provides
    @Reusable
    fun provideGetLaunches(
        spaceXRepository: SpaceXRepository,
        filter: LaunchesDomainFilter
    ): GetLaunches = GetLaunchesImpl(spaceXRepository, filter)

    @Provides
    @Reusable
    fun provideGetCompanyInfo(
        spaceXRepository: SpaceXRepository
    ): GetCompanyInfo = GetCompanyInfoImpl(spaceXRepository)

    @Provides
    @Reusable
    fun provideLaunchesDomainFilter(): LaunchesDomainFilter =
        LaunchesDomainFilterImpl()
}
