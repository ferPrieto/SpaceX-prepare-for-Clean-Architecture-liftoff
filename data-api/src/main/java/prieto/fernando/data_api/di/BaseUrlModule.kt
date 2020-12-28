package prieto.fernando.data_api.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
object BaseUrlModule {
    @Provides
    @BaseUrl
    fun providesBaseUrl(): String = "https://api.spacexdata.com/v3/"
}