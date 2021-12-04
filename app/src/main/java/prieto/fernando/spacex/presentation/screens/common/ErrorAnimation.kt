package prieto.fernando.spacex.presentation.screens.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import prieto.fernando.spacex.R
import prieto.fernando.spacex.theme.Dark
import prieto.fernando.spacex.theme.Light
import prieto.fernando.spacex.theme.SpaceXTypography

@Composable
fun ErrorAnimation(
    errorComposition: LottieComposition?,
    errorProgress: Float
) {
    Box {
        Text(
            text = stringResource(id = R.string.launches_error_occurred),
            style = SpaceXTypography.h3,
            modifier = Modifier
                .padding(top = 40.dp)
                .align(Alignment.TopCenter),
            color = if (MaterialTheme.colors.isLight) Light.Accent
            else Dark.Accent
        )
        LottieAnimation(
            composition = errorComposition,
            progress = errorProgress,
            alignment = Alignment.BottomCenter
        )
    }
}