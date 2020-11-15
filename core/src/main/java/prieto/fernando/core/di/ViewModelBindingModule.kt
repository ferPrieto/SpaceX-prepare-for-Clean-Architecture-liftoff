package prieto.fernando.core.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import prieto.fernando.core.android.InjectingViewModelFactory

@Module
@InstallIn(ApplicationComponent::class)
abstract class ViewModelBindingModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: InjectingViewModelFactory): ViewModelProvider.Factory
}
