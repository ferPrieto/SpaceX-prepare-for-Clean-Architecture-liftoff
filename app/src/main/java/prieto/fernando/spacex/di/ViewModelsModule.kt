package prieto.fernando.spacex.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import prieto.fernando.domain.usecase.GetCompanyInfo
import prieto.fernando.domain.usecase.GetLaunches
import prieto.fernando.spacex.presentation.vm.DashboardViewModelImpl
import prieto.fernando.spacex.presentation.vm.LaunchesViewModelImpl
import prieto.fernando.spacex.presentation.vm.mapper.CompanyInfoDomainToUiModelMapper
import prieto.fernando.spacex.presentation.vm.mapper.LaunchesDomainToUiModelMapper

@Module
@InstallIn(ActivityRetainedComponent::class)
class ViewModelsModule {
    @Provides
    fun provideDashboardViewModel(
        getCompanyInfo: GetCompanyInfo,
        companyInfoDomainToUiModelMapper: CompanyInfoDomainToUiModelMapper
    ) = DashboardViewModelImpl(getCompanyInfo, companyInfoDomainToUiModelMapper)

    @Provides
    fun provideLaunchesViewModel(
        getLaunches: GetLaunches,
        launchesDomainToUiModelMapper: LaunchesDomainToUiModelMapper
    ) = LaunchesViewModelImpl(getLaunches, launchesDomainToUiModelMapper)
}
