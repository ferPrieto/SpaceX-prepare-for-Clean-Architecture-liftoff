package prieto.fernando.spacex.presentation.vm.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import prieto.fernando.spacex.presentation.vm.mapper.CompanyInfoDomainToUiModelMapper
import prieto.fernando.spacex.presentation.vm.mapper.CompanyInfoDomainToUiModelMapperImpl
import prieto.fernando.spacex.presentation.vm.mapper.DateTimeProvider
import prieto.fernando.spacex.presentation.vm.mapper.DateTransformer
import prieto.fernando.spacex.presentation.vm.mapper.DateTransformerImpl
import prieto.fernando.spacex.presentation.vm.mapper.LaunchesDomainToUiModelMapper
import prieto.fernando.spacex.presentation.vm.mapper.LaunchesDomainToUiModelMapperImpl

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {
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
