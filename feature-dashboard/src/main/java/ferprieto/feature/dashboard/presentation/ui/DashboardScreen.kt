package ferprieto.feature.dashboard.presentation.ui

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
import ferprieto.feature.dashboard.R
import ferprieto.feature.dashboard.presentation.vm.DashboardContract
import ferprieto.shared.ui.components.ErrorAnimation
import ferprieto.shared.ui.components.LoadingAnimation
import ferprieto.shared.ui.theme.SpaceX

@Composable
fun DashboardScreen(
    state: DashboardContract.State
) {
    val bodyComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            ferprieto.shared.ui.R.raw.planet_animation
        )
    )
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
                LoadingAnimation(
                    animationResId = ferprieto.shared.ui.R.raw.loading_animation,
                    modifier = Modifier
                )
            }
            state.isError -> {
                val errorComposition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(
                        ferprieto.shared.ui.R.raw.error_conection
                    )
                )
                val errorProgress by animateLottieCompositionAsState(errorComposition)
                
                // TODO: Move error string to shared-ui or create feature-specific string
                ErrorAnimation(
                    errorComposition = errorComposition,
                    errorProgress = errorProgress,
                    errorMessageResId = ferprieto.shared.ui.R.string.error_occurred
                )
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

