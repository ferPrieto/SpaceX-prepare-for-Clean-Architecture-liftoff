package prieto.fernando.spacex.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import prieto.fernando.spacex.presentation.theme.SpaceXTheme

@AndroidEntryPoint
class EntryPointActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceXTheme {
                MainScreen()
            }
        }
    }
}
