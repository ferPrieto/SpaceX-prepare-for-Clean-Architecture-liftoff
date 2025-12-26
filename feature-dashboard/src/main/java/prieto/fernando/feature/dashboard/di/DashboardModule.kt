package prieto.fernando.feature.dashboard.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import prieto.fernando.feature.dashboard.data.mapper.CompanyInfoRepositoryToDomainModelMapper
import prieto.fernando.feature.dashboard.data.mapper.CompanyInfoRepositoryToDomainModelMapperImpl
import prieto.fernando.feature.dashboard.data.mapper.CompanyInfoResponseToRepositoryMapper
import prieto.fernando.feature.dashboard.data.mapper.CompanyInfoResponseToRepositoryMapperImpl
import prieto.fernando.feature.dashboard.data.repository.CompanyInfoRepositoryImpl
import prieto.fernando.feature.dashboard.domain.repository.CompanyInfoRepository
import prieto.fernando.feature.dashboard.domain.usecase.GetCompanyInfo
import prieto.fernando.feature.dashboard.domain.usecase.GetCompanyInfoImpl
import prieto.fernando.feature.dashboard.presentation.mapper.CompanyInfoDomainToUiModelMapper
import prieto.fernando.feature.dashboard.presentation.mapper.CompanyInfoDomainToUiModelMapperImpl
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class DashboardViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGetCompanyInfo(
        impl: GetCompanyInfoImpl
    ): GetCompanyInfo

    @Binds
    @ViewModelScoped
    abstract fun bindCompanyInfoDomainToUiModelMapper(
        impl: CompanyInfoDomainToUiModelMapperImpl
    ): CompanyInfoDomainToUiModelMapper
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DashboardDataModule {

    @Binds
    @Singleton
    abstract fun bindCompanyInfoRepository(
        impl: CompanyInfoRepositoryImpl
    ): CompanyInfoRepository

    @Binds
    @Singleton
    abstract fun bindCompanyInfoResponseToRepositoryMapper(
        impl: CompanyInfoResponseToRepositoryMapperImpl
    ): CompanyInfoResponseToRepositoryMapper

    @Binds
    @Singleton
    abstract fun bindCompanyInfoRepositoryToDomainMapper(
        impl: CompanyInfoRepositoryToDomainModelMapperImpl
    ): CompanyInfoRepositoryToDomainModelMapper
}

