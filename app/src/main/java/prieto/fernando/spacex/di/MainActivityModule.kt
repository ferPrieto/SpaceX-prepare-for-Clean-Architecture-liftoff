package prieto.fernando.spacex.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import prieto.fernando.core.di.FragmentKey
import prieto.fernando.core.di.ViewModelKey
import prieto.fernando.presentation.DashboardViewModel
import prieto.fernando.presentation.DashboardViewModelImpl
import prieto.fernando.presentation.LaunchesViewModel
import prieto.fernando.presentation.LaunchesViewModelImpl
import prieto.fernando.spacex.ui.DashboardFragment
import prieto.fernando.spacex.ui.LaunchesFragment
import prieto.fernando.spacex.ui.MainActivity

@Module
internal abstract class MainActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @Binds
    @IntoMap
    @FragmentKey(DashboardFragment::class)
    abstract fun dashboardFragment(dashboardFragment: DashboardFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LaunchesFragment::class)
    abstract fun launchesFragment(launchesFragment: LaunchesFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    @ExperimentalCoroutinesApi
    abstract fun dashboardViewModel(dashboardViewModel: DashboardViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LaunchesViewModel::class)
    @ExperimentalCoroutinesApi
    abstract fun launchesViewModel(launchesViewModel: LaunchesViewModelImpl): ViewModel
}
