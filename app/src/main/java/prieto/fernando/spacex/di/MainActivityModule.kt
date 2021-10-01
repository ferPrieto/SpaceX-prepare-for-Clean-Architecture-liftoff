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
import prieto.fernando.presentation.MainViewModel
import prieto.fernando.presentation.MainViewModelImpl
import prieto.fernando.spacex.ui.DashboardFragment
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
    @ViewModelKey(MainViewModel::class)
    @ExperimentalCoroutinesApi
    abstract fun dashboardViewModel(viewModel: MainViewModelImpl): ViewModel
}
