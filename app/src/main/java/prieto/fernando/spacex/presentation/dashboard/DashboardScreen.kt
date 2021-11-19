package prieto.fernando.spacex.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import prieto.fernando.spacex.R
import prieto.fernando.spacex.presentation.theme.Dark
import prieto.fernando.spacex.presentation.theme.Light
import prieto.fernando.spacex.presentation.theme.SpaceXTypography

@Composable
fun DashboardScreen(
    state: DashboardContract.State
) {
    val loadingComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
    val loadingProgress by animateLottieCompositionAsState(loadingComposition)

    val errorComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_conection))
    val errorProgress by animateLottieCompositionAsState(errorComposition)

    val bodyComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.planet_animation))
    val bodyProgress by animateLottieCompositionAsState(bodyComposition)

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(
                if (MaterialTheme.colors.isLight) Light.Background
                else Dark.Background
            )
    ) {
        Text(
            text = stringResource(id = R.string.company_title),
            modifier = Modifier.padding(16.dp),
            style = SpaceXTypography.h1,
            color = if (MaterialTheme.colors.isLight) Light.TextColorPrimary
            else Dark.TextColorPrimary
        )
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
                    modifier = Modifier.padding(16.dp),
                    color = if (MaterialTheme.colors.isLight) Light.TextColorSecondary
                    else Dark.TextColorSecondary
                )
                LottieAnimation(
                    bodyComposition,
                    bodyProgress,
                    modifier = Modifier
                        .size(260.dp)
                        .align(Alignment.CenterHorizontally)
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