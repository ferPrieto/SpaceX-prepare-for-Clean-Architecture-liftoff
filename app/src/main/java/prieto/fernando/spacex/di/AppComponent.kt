package prieto.fernando.spacex.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import prieto.fernando.data_api.di.ApiModule
import prieto.fernando.data_api.di.NetworkModule
import prieto.fernando.data_repository.di.SpaceXRepositoryModule
import prieto.fernando.domain.di.DomainModule
import prieto.fernando.spacex.SpaceXApp
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ApiModule::class,
        MainActivityModule::class,
        NetworkModule::class,
        DomainModule::class,
        SpaceXRepositoryModule::class]
)
@Singleton
interface AppComponent : AndroidInjector<SpaceXApp>
