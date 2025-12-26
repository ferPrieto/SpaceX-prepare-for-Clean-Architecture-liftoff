package prieto.fernando.feature.launches.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import prieto.fernando.feature.launches.data.mapper.LaunchesRepositoryToDomainModelMapper
import prieto.fernando.feature.launches.data.mapper.LaunchesRepositoryToDomainModelMapperImpl
import prieto.fernando.feature.launches.data.mapper.LaunchesResponseToRepositoryMapper
import prieto.fernando.feature.launches.data.mapper.LaunchesResponseToRepositoryMapperImpl
import prieto.fernando.feature.launches.data.repository.LaunchesRepositoryImpl
import prieto.fernando.feature.launches.domain.mapper.LaunchesDomainFilter
import prieto.fernando.feature.launches.domain.mapper.LaunchesDomainFilterImpl
import prieto.fernando.feature.launches.domain.repository.LaunchesRepository
import prieto.fernando.feature.launches.domain.usecase.GetLaunches
import prieto.fernando.feature.launches.domain.usecase.GetLaunchesImpl
import prieto.fernando.feature.launches.presentation.mapper.DateTransformer
import prieto.fernando.feature.launches.presentation.mapper.DateTransformerImpl
import prieto.fernando.feature.launches.presentation.mapper.LaunchesDomainToUiModelMapper
import prieto.fernando.feature.launches.presentation.mapper.LaunchesDomainToUiModelMapperImpl
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class LaunchesViewModelModule {

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

@Module
@InstallIn(SingletonComponent::class)
abstract class LaunchesDataModule {

    @Binds
    @Singleton
    abstract fun bindLaunchesRepository(
        impl: LaunchesRepositoryImpl
    ): LaunchesRepository

    @Binds
    @Singleton
    abstract fun bindLaunchesResponseToRepositoryMapper(
        impl: LaunchesResponseToRepositoryMapperImpl
    ): LaunchesResponseToRepositoryMapper

    @Binds
    @Singleton
    abstract fun bindLaunchesRepositoryToDomainMapper(
        impl: LaunchesRepositoryToDomainModelMapperImpl
    ): LaunchesRepositoryToDomainModelMapper
}
