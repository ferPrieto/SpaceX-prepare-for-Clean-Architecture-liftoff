package prieto.fernando.spacex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import prieto.fernando.feature.navigation.MainScreen
import prieto.fernando.shared.ui.theme.SpaceX

@AndroidEntryPoint
class EntryPointActivity : ComponentActivity() {

    @OptIn(InternalCoroutinesApi::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceX.SpaceXTheme {
                MainScreen()
            }
        }
    }
}
