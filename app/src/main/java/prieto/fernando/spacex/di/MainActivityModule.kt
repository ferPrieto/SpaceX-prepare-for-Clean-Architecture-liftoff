package prieto.fernando.spacex.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import prieto.fernando.core.presentation.ViewModelProviderFactory
import prieto.fernando.presentation.MainViewModel
import prieto.fernando.spacex.ui.DashboardFragment
import prieto.fernando.spacex.ui.MainActivity

@Module
internal abstract class MainActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun bindDashboardFragment(): DashboardFragment

    @Module
    companion object {
        @Provides
        @JvmStatic
        internal fun provideMainViewModelFactory(viewModel: MainViewModel): ViewModelProviderFactory<MainViewModel> {
            return ViewModelProviderFactory(viewModel)
        }
    }
}
