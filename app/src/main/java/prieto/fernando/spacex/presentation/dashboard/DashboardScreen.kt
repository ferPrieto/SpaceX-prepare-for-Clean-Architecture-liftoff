package prieto.fernando.spacex.presentation.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import prieto.fernando.spacex.R
import java.lang.StringBuilder

@Composable
fun DashboardScreen(
    state: DashboardContract.State,
    onEventSent: (event: DashboardContract.Event) -> Unit,
) {
    Box {
        CompanyInfo(companyInfo = state.companyInfo)
       /* if (state.isLoading)
           show Lottie Animation*/
    }
}

@Composable
private fun CompanyInfo(companyInfo: CompanyInfo) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = stringResource(id = R.string.company_title), modifier = Modifier.padding(16.dp))
        Text(text = BindCompanyInfo(companyInfo), modifier = Modifier.padding(16.dp))
    }
}

@Composable
private fun BindCompanyInfo(companyInfo: CompanyInfo): String =
    String.format(
        stringResource(id = R.string.company_data),
        companyInfo.name,
        companyInfo.founder,
        companyInfo.foundedYear,
        companyInfo.employees,
        companyInfo.launchSites,
        companyInfo.valuation
    )