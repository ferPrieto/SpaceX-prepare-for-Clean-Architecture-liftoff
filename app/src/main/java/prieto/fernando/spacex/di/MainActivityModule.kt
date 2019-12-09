package prieto.fernando.spacex.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import prieto.fernando.spacex.ui.MainActivity

@Module
internal abstract class MainActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity

    @Module
    companion object {
    }
}
