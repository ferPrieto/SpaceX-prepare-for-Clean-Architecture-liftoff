package prieto.fernando.feature.launches.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import prieto.fernando.feature.launches.presentation.mapper.LaunchesDomainToUiModelMapper
import prieto.fernando.feature.launches.presentation.mapper.LaunchesDomainToUiModelMapperImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class LaunchesModule {

    @Binds
    @ViewModelScoped
    abstract fun bindLaunchesDomainToUiModelMapper(
        impl: LaunchesDomainToUiModelMapperImpl
    ): LaunchesDomainToUiModelMapper
}
