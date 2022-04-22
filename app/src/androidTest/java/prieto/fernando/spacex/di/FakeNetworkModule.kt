package prieto.fernando.spacex.di

import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import prieto.fernando.data_api.BuildConfig
import prieto.fernando.data_api.di.NetworkModule

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
class FakeNetworkModule : NetworkModule() {
    override fun getBaseUrl() = "http://127.0.0.1:${BuildConfig.PORT}"
}
