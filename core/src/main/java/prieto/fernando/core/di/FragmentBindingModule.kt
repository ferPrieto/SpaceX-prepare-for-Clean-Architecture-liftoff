package prieto.fernando.core.di

import androidx.fragment.app.FragmentFactory
import dagger.Binds
import dagger.Module
import prieto.fernando.core.android.InjectingFragmentFactory

@Module
abstract class FragmentBindingModule {
    @Binds
    abstract fun bindFragmentFactory(factory: InjectingFragmentFactory): FragmentFactory
}
