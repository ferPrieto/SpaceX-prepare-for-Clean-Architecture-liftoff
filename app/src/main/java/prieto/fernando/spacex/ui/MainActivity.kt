package prieto.fernando.spacex.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentFactory
import dagger.android.support.DaggerAppCompatActivity
import prieto.fernando.spacex.ui.theme.SpaceXTheme
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.fragmentFactory = fragmentFactory
        setContent {
            SpaceXTheme {
                MainScreen()
            }
        }
    }
}
