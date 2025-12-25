package prieto.fernando.spacex.presentation.screens.error

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.airbnb.lottie.LottieComposition
import prieto.fernando.spacex.R
import prieto.fernando.shared.ui.components.ErrorAnimation as SharedErrorAnimation

@Composable
fun ErrorAnimation(
    errorComposition: LottieComposition?,
    errorProgress: Float,
    modifier: Modifier = Modifier
) {
    SharedErrorAnimation(
        errorComposition = errorComposition,
        errorProgress = errorProgress,
        errorMessageResId = R.string.launches_error_occurred,
        modifier = modifier
    )
}

