package prieto.fernando.spacex

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import prieto.fernando.spacex.di.DaggerAppComponent

open class SpaceXApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder()
            .build()
}
