package prieto.fernando.spacex.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import prieto.fernando.data_api.di.ApiModule
import prieto.fernando.data_api.di.NetworkModule
import prieto.fernando.spacex.SpaceXApp
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ApiModule::class,
        NetworkModule::class,
        MainActivityModule::class]
)
@Singleton
interface AppComponent : AndroidInjector<SpaceXApp>
