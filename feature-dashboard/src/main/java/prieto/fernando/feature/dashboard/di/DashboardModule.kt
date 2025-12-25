package prieto.fernando.feature.dashboard.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import prieto.fernando.feature.dashboard.presentation.mapper.CompanyInfoDomainToUiModelMapper
import prieto.fernando.feature.dashboard.presentation.mapper.CompanyInfoDomainToUiModelMapperImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class DashboardModule {

    @Binds
    @ViewModelScoped
    abstract fun bindCompanyInfoDomainToUiModelMapper(
        impl: CompanyInfoDomainToUiModelMapperImpl
    ): CompanyInfoDomainToUiModelMapper
}

