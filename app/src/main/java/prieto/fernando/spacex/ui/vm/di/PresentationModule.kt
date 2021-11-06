package prieto.fernando.spacex.ui.vm.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import prieto.fernando.spacex.ui.vm.mapper.*

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