package prieto.fernando.spacex.presentation.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import prieto.fernando.spacex.R

@Composable
fun DashboardScreen(
    state: DashboardContract.State,
    onEventSent: (event: DashboardContract.Event) -> Unit,
) {
    val loadingComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
    val loadingProgress by animateLottieCompositionAsState(loadingComposition)
    val errorComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_conection))
    val errorProgress by animateLottieCompositionAsState(errorComposition)
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = stringResource(id = R.string.company_title), modifier = Modifier.padding(16.dp))
        when {
            state.isLoading -> {
                LottieAnimation(
                    loadingComposition,
                    loadingProgress,
                )
            }
            state.isError -> {
                LottieAnimation(
                    errorComposition,
                    errorProgress,
                )
            }
            else -> {
                Text(
                    text = fillCompanyInfo(state.companyInfo),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun fillCompanyInfo(companyInfo: CompanyInfo): String =
    String.format(
        stringResource(id = R.string.company_data),
        companyInfo.name,
        companyInfo.founder,
        companyInfo.foundedYear,
        companyInfo.employees,
        companyInfo.launchSites,
        companyInfo.valuation
    )