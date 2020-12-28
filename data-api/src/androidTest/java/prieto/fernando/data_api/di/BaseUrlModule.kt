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
    fun provideBaseUrl(): String = "http://127.0.0.1:8080"
}