package ferprieto.feature.dashboard.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import ferprieto.feature.dashboard.data.mapper.CompanyInfoRepositoryToDomainModelMapper
import ferprieto.feature.dashboard.data.mapper.CompanyInfoRepositoryToDomainModelMapperImpl
import ferprieto.feature.dashboard.data.mapper.CompanyInfoResponseToRepositoryMapper
import ferprieto.feature.dashboard.data.mapper.CompanyInfoResponseToRepositoryMapperImpl
import ferprieto.feature.dashboard.data.repository.CompanyInfoRepositoryImpl
import ferprieto.feature.dashboard.domain.repository.CompanyInfoRepository
import ferprieto.feature.dashboard.domain.usecase.GetCompanyInfo
import ferprieto.feature.dashboard.domain.usecase.GetCompanyInfoImpl
import ferprieto.feature.dashboard.presentation.vm.mapper.CompanyInfoDomainToUiModelMapper
import ferprieto.feature.dashboard.presentation.vm.mapper.CompanyInfoDomainToUiModelMapperImpl
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

