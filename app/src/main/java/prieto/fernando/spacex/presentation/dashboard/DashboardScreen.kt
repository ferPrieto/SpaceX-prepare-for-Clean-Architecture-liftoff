package prieto.fernando.spacex.presentation.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import prieto.fernando.spacex.presentation.vm.DashboardViewModel
import prieto.fernando.spacex.R

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel
) {
   // val viewState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = stringResource(id = R.string.company_title), modifier = Modifier.padding(16.dp))
        Text(text = stringResource(id = R.string.company_data), modifier = Modifier.padding(16.dp))
    }
}