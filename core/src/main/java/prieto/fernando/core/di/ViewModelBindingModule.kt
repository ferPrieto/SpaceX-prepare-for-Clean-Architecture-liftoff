package prieto.fernando.core.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import prieto.fernando.core.android.InjectingViewModelFactory

@Module
abstract class ViewModelBindingModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: InjectingViewModelFactory): ViewModelProvider.Factory
}
