package prieto.fernando.domain.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import prieto.fernando.domain.SpaceXRepository
import prieto.fernando.domain.mapper.*
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
    fun provideLaunchesDomainToUiModelMapper(
        dateTransformer: DateTransformer
    ): LaunchesDomainToUiModelMapper = LaunchesDomainToUiModelMapperImpl(dateTransformer)

    @Provides
    @Reusable
    fun provideDateTransformer(
        dateTimeProvider: DateTimeProvider
    ): DateTransformer = DateTransformerImpl(dateTimeProvider)

    @Provides
    @Reusable
    fun provideDateTimeProvider(): DateTimeProvider = DateTimeProvider()

    @Provides
    @Reusable
    fun provideCompanyInfoDomainToUiModelMapper(): CompanyInfoDomainToUiModelMapper =
        CompanyInfoDomainToUiModelMapperImpl()
}
