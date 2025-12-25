package prieto.fernando.feature.launches.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import prieto.fernando.feature.launches.domain.mapper.LaunchesDomainFilter
import prieto.fernando.feature.launches.domain.mapper.LaunchesDomainFilterImpl
import prieto.fernando.feature.launches.domain.usecase.GetLaunches
import prieto.fernando.feature.launches.domain.usecase.GetLaunchesImpl
import prieto.fernando.feature.launches.presentation.mapper.DateTransformer
import prieto.fernando.feature.launches.presentation.mapper.DateTransformerImpl
import prieto.fernando.feature.launches.presentation.mapper.LaunchesDomainToUiModelMapper
import prieto.fernando.feature.launches.presentation.mapper.LaunchesDomainToUiModelMapperImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class LaunchesModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGetLaunches(
        impl: GetLaunchesImpl
    ): GetLaunches

    @Binds
    @ViewModelScoped
    abstract fun bindLaunchesDomainFilter(
        impl: LaunchesDomainFilterImpl
    ): LaunchesDomainFilter

    @Binds
    @ViewModelScoped
    abstract fun bindLaunchesDomainToUiModelMapper(
        impl: LaunchesDomainToUiModelMapperImpl
    ): LaunchesDomainToUiModelMapper

    @Binds
    @ViewModelScoped
    abstract fun bindDateTransformer(
        impl: DateTransformerImpl
    ): DateTransformer
}
