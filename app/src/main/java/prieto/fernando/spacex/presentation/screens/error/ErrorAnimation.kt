package prieto.fernando.spacex.presentation.screens.error

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import prieto.fernando.spacex.R
import prieto.fernando.spacex.theme.SpaceX

@Composable
fun ErrorAnimation(
    errorComposition: LottieComposition?,
    errorProgress: Float,
    modifier: Modifier = Modifier
) {
    Box {
        Text(
            text = stringResource(id = R.string.launches_error_occurred),
            style = SpaceX.LocalTypography.current.h3,
            modifier = modifier
                .padding(top = 40.dp)
                .align(Alignment.TopCenter),
            color = SpaceX.LocalColors.current.accent
        )
        LottieAnimation(
            composition = errorComposition,
            progress = errorProgress,
            alignment = Alignment.BottomCenter,
            modifier = modifier.semantics { contentDescription = "404 Animation" }
        )
    }
}
