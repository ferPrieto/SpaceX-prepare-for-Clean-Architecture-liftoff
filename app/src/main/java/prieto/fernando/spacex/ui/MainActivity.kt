package prieto.fernando.spacex.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import prieto.fernando.spacex.ui.theme.SpaceXTheme
import prieto.fernando.spacex.ui.vm.DashboardViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SpaceXTheme {
                MainScreen()
            }
        }
    }
}
