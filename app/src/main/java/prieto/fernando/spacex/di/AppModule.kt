package prieto.fernando.spacex.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
open class AppModule {

 /*   @Provides
    fun provideContext(@ApplicationContext app: Application): Context = app.applicationContext

    @Provides
    fun provideResources(app: Application): Resources = app.resources*/
}
