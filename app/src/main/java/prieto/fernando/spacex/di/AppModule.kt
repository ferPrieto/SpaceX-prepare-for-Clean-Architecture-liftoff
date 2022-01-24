package prieto.fernando.spacex.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
open class AppModule {

 /*   @Provides
    fun provideContext(@ApplicationContext app: Application): Context = app.applicationContext

    @Provides
    fun provideResources(app: Application): Resources = app.resources*/
}
