package ferprieto.feature.launches.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import ferprieto.feature.launches.data.mapper.LaunchesRepositoryToDomainModelMapper
import ferprieto.feature.launches.data.mapper.LaunchesRepositoryToDomainModelMapperImpl
import ferprieto.feature.launches.data.mapper.LaunchesResponseToRepositoryMapper
import ferprieto.feature.launches.data.mapper.LaunchesResponseToRepositoryMapperImpl
import ferprieto.feature.launches.data.repository.LaunchesRepositoryImpl
import ferprieto.feature.launches.domain.mapper.LaunchesDomainFilter
import ferprieto.feature.launches.domain.mapper.LaunchesDomainFilterImpl
import ferprieto.feature.launches.domain.repository.LaunchesRepository
import ferprieto.feature.launches.domain.usecase.GetLaunches
import ferprieto.feature.launches.domain.usecase.GetLaunchesImpl
import ferprieto.feature.launches.presentation.vm.mapper.DateTransformer
import ferprieto.feature.launches.presentation.vm.mapper.DateTransformerImpl
import ferprieto.feature.launches.presentation.vm.mapper.LaunchesDomainToUiModelMapper
import ferprieto.feature.launches.presentation.vm.mapper.LaunchesDomainToUiModelMapperImpl
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
