package prieto.fernando.spacex.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides

@Module
open class AppModule {

    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    fun provideResources(app: Application): Resources = app.resources
}
