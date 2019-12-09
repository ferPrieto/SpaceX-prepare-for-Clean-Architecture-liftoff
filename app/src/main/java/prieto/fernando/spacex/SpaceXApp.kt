package prieto.fernando.spacex

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class SpaceXApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder()
            .build()
}
