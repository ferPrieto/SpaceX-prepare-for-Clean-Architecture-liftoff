package ferprieto.spacex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import dagger.hilt.android.AndroidEntryPoint
import ferprieto.feature.navigation.MainScreen
import ferprieto.shared.ui.theme.SpaceX
import kotlinx.coroutines.InternalCoroutinesApi

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
