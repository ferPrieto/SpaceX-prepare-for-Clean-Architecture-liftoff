package prieto.fernando.spacex.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import prieto.fernando.core.di.FragmentBindingModule
import prieto.fernando.core.di.ViewModelBindingModule
import prieto.fernando.data.di.SpaceXRepositoryModule
import prieto.fernando.data_api.di.ApiModule
import prieto.fernando.data_api.di.NetworkModule
import prieto.fernando.domain.di.DomainModule
import prieto.fernando.presentation.di.PresentationModule
import prieto.fernando.spacex.SpaceXApp
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ApiModule::class,
        MainActivityModule::class,
        PresentationModule::class,
        NetworkModule::class,
        DomainModule::class,
        SpaceXRepositoryModule::class,
        ViewModelBindingModule::class,
        FragmentBindingModule::class
    ]
)
@Singleton
interface AppComponent  
