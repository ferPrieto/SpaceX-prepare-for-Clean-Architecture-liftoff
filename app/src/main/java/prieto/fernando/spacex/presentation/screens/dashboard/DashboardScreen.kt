package prieto.fernando.spacex.presentation.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import prieto.fernando.spacex.R
import prieto.fernando.spacex.presentation.screens.error.ErrorAnimation
import prieto.fernando.spacex.theme.SpaceX

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
            .background(SpaceX.LocalColors.current.background)
    ) {
        Text(
            text = stringResource(id = R.string.company_title),
            modifier = Modifier.padding(16.dp),
            style = SpaceX.LocalTypography.current.h1,
            color = SpaceX.LocalColors.current.textColorPrimary
        )
        when {
            state.isLoading -> {
                LottieAnimation(
                    composition = loadingComposition,
                    progress = loadingProgress,
                    modifier = Modifier.semantics { contentDescription = "Loading Animation" }
                )
            }
            state.isError -> {
                ErrorAnimation(errorComposition, errorProgress)
            }
            else -> {
                Text(
                    text = fillCompanyInfo(state.companyInfoUiModel),
                    modifier = Modifier.padding(16.dp),
                    style = SpaceX.LocalTypography.current.h4,
                    color = SpaceX.LocalColors.current.textColorSecondary
                )
                LottieAnimation(
                    composition = bodyComposition,
                    progress = bodyProgress,
                    modifier = Modifier
                        .size(260.dp)
                        .align(Alignment.CenterHorizontally)
                        .semantics { contentDescription = "Planet Animation" }
                )
            }
        }
    }
}

@Composable
private fun fillCompanyInfo(companyInfoUiModel: CompanyInfoUiModel): String =
    String.format(
        stringResource(id = R.string.company_data),
        companyInfoUiModel.name,
        companyInfoUiModel.founder,
        companyInfoUiModel.foundedYear,
        companyInfoUiModel.employees,
        companyInfoUiModel.launchSites,
        companyInfoUiModel.valuation
    )
