package prieto.fernando.feature.navigation

import androidx.compose.runtime.Composable
import prieto.fernando.feature.dashboard.presentation.DashboardScreen
import prieto.fernando.feature.dashboard.presentation.DashboardViewModel
import prieto.fernando.feature.launches.presentation.LaunchesScreen
import prieto.fernando.feature.launches.presentation.LaunchesViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi

@OptIn(ExperimentalMaterialApi::class, InternalCoroutinesApi::class)
@Composable
fun resolveNavKeyToContent(
    key: NavKey,
    paddingValues: PaddingValues = PaddingValues(),
    onLinkClicked: (String) -> Unit = {}
): @Composable () -> Unit = when (key) {
    NavKey.Dashboard -> {
        {
            val viewModel: DashboardViewModel = hiltViewModel()
            DashboardScreen(state = viewModel.viewState.value)
        }
    }
    NavKey.Launches -> {
        {
            val viewModel: LaunchesViewModel = hiltViewModel()
            val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
            )
            val coroutineScope = rememberCoroutineScope()
            val youTubeLinkState = remember { mutableStateOf("") }
            val wikipediaLinkState = remember { mutableStateOf("") }

            LaunchesBottomSheetLayout(
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                coroutineScope = coroutineScope,
                launchesViewModel = viewModel,
                paddingValues = paddingValues,
                youTubeLinkState = youTubeLinkState,
                wikipediaLinkState = wikipediaLinkState,
                onLinkClicked = onLinkClicked
            )
        }
    }
}

