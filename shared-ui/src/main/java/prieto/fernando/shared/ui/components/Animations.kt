package prieto.fernando.shared.ui.components

import androidx.annotation.RawRes
import androidx.annotation.StringRes
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
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import prieto.fernando.shared.ui.theme.SpaceX

@Composable
fun ErrorAnimation(
    errorComposition: LottieComposition?,
    errorProgress: Float,
    @StringRes errorMessageResId: Int,
    modifier: Modifier = Modifier
) {
    Box {
        Text(
            text = stringResource(id = errorMessageResId),
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

@Composable
fun LoadingAnimation(
    @RawRes animationResId: Int,
    modifier: Modifier = Modifier,
    contentDescriptionText: String = "Loading Animation"
) {
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(animationResId))
    val progress = animateLottieCompositionAsState(composition.value, iterations = Int.MAX_VALUE)
    
    LottieAnimation(
        composition = composition.value,
        progress = progress.value,
        modifier = modifier.semantics { contentDescription = contentDescriptionText }
    )
}

@Composable
fun EmptyStateAnimation(
    @RawRes animationResId: Int,
    @StringRes messageResId: Int,
    modifier: Modifier = Modifier
) {
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(animationResId))
    val progress = animateLottieCompositionAsState(composition.value, iterations = Int.MAX_VALUE)
    
    Box(modifier = modifier) {
        Text(
            text = stringResource(id = messageResId),
            style = SpaceX.LocalTypography.current.h3,
            modifier = Modifier
                .padding(top = 40.dp)
                .align(Alignment.TopCenter),
            color = SpaceX.LocalColors.current.textColorSecondary
        )
        LottieAnimation(
            composition = composition.value,
            progress = progress.value,
            alignment = Alignment.BottomCenter,
            modifier = Modifier.semantics { contentDescription = "Empty State Animation" }
        )
    }
}

