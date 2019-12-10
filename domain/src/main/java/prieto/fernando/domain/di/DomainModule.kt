package prieto.fernando.domain.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.mapper.CompanyInfoDomainToUiModelMapper
import prieto.fernando.domain.mapper.CompanyInfoDomainToUiModelMapperImpl
import prieto.fernando.domain.mapper.LaunchesDomainToUiModelMapper
import prieto.fernando.domain.mapper.LaunchesDomainToUiModelMapperImpl
import prieto.fernando.domain.usecase.GetCompanyInfoImpl
import prieto.fernando.domain.usecase.GetLaunchesImpl
import prieto.fernando.presentation.GetCompanyInfo
import prieto.fernando.presentation.GetLaunches

@Module
class DomainModule {
    @Provides
    @Reusable
    fun provideGetLaunches(
        spaceXRepository: SpaceXRepository,
        launchesDomainToUiModelMapper: LaunchesDomainToUiModelMapper
    ): GetLaunches = GetLaunchesImpl(spaceXRepository, launchesDomainToUiModelMapper)

    @Provides
    @Reusable
    fun provideGetCompanyInfo(
        spaceXRepository: SpaceXRepository,
        companyUiModelMapper: CompanyInfoDomainToUiModelMapper
    ): GetCompanyInfo = GetCompanyInfoImpl(spaceXRepository, companyUiModelMapper)

    @Provides
    @Reusable
    fun provideLaunchesDomainToUiModelMapper(): LaunchesDomainToUiModelMapper =
        LaunchesDomainToUiModelMapperImpl()

    @Provides
    @Reusable
    fun provideCompanyInfoDomainToUiModelMapper(): CompanyInfoDomainToUiModelMapper =
        CompanyInfoDomainToUiModelMapperImpl()
}
