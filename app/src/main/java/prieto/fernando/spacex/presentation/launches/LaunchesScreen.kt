package prieto.fernando.spacex.presentation.launches

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import prieto.fernando.spacex.R
import prieto.fernando.spacex.presentation.theme.Dark
import prieto.fernando.spacex.presentation.theme.Light
import prieto.fernando.spacex.presentation.theme.SpaceXTypography

@Composable
fun LaunchesScreen(
    state: LaunchesContract.State
) {
    val loadingComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
    val loadingProgress by animateLottieCompositionAsState(loadingComposition)

    val errorComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_conection))
    val errorProgress by animateLottieCompositionAsState(errorComposition)

    val bodyComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.rocket_launched))
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
            text = stringResource(id = R.string.launches_title),
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
                Box {
                    LaunchesList(launchesItems = state.launches) { links ->
                        // Show bottom Tray
                    }
                }
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
fun LaunchesList(
    launchesItems: List<Launch>,
    onItemClicked: (id: Links) -> Unit = { }
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(launchesItems) { item ->
            LaunchItemRow(launchItem = item, onItemClicked = onItemClicked)
        }
    }
}


@Composable
fun LaunchItemRow(
    launchItem: Launch,
    iconTransformationBuilder: ImageRequest.Builder.() -> Unit = { },
    onItemClicked: (links: Links) -> Unit = { }
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = if (MaterialTheme.colors.isLight) Light.ItemBackground
        else Dark.ItemBackground,
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClicked(launchItem.links) }
    ) {
        Row {
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(start = 8.dp)
            ) {
                LaunchItemThumbnail(launchItem.links.missionPatchSmall, iconTransformationBuilder)
            }
            LaunchItemTags(
                item = launchItem,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .align(Alignment.CenterVertically)
            )
            LaunchItemContent(
                launchItem = launchItem,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .align(Alignment.CenterVertically)
            )
            SuccessIcon(
                launchItem = launchItem,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .align(Alignment.Top)
            )
        }
    }
}

@Composable
private fun SuccessIcon(
    launchItem: Launch,
    modifier: Modifier
) {
    Box(modifier) {
        Image(
            painter = painterResource(getSuccessDrawable(launchItem.launchSuccess)),
            contentDescription = "Success or Failure launch Icon"
        )
    }
}

@Composable
private fun LaunchItemContent(
    launchItem: Launch,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        LaunchItemContentText(launchItem.missionName)
        LaunchItemContentText(launchItem.launchDate)
        LaunchItemContentText("${launchItem.rocket.rocketName}/${launchItem.rocket.rocketType}")
        LaunchItemContentText("+/-${launchItem.differenceOfDays}")
    }
}

@Composable
fun LaunchItemThumbnail(
    thumbnailUrl: String,
    iconTransformationBuilder: ImageRequest.Builder.() -> Unit
) {
    Image(
        painter = rememberImagePainter(
            data = thumbnailUrl,
            builder = { size(150, 150) }
        ),
        modifier = Modifier,
        contentDescription = "Launch item thumbnail picture"
    )
}

@Composable
fun LaunchItemTags(
    item: Launch,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        LaunchItemTagText(R.string.launches_item_mission)
        LaunchItemTagText(R.string.launches_item_date_time)
        LaunchItemTagText(R.string.launches_item_rocket)
        LaunchItemTagText(getTitleSinceFrom(item.isPastLaunch))
    }
}

@Composable
private fun LaunchItemTagText(stringResource: Int) {
    Text(
        text = stringResource(id = stringResource),
        textAlign = TextAlign.Start,
        style = SpaceXTypography.subtitle1,
        maxLines = 1,
        color = if (MaterialTheme.colors.isLight) Light.TextColorSecondary
        else Dark.TextColorSecondary
    )
}

private fun getTitleSinceFrom(isPastLaunch: Boolean) =
    if (isPastLaunch) {
        R.string.company_data_since
    } else {
        R.string.company_data_from
    }

@Composable
private fun LaunchItemContentText(text: String) {
    Text(
        text = text,
        textAlign = TextAlign.End,
        style = SpaceXTypography.subtitle2,
        maxLines = 1,
        color = if (MaterialTheme.colors.isLight) Light.TextHighlighted
        else Dark.TextHighlighted
    )
}

private fun getSuccessDrawable(launchSuccess: Boolean) =
    if (launchSuccess) {
        R.drawable.ic_check
    } else {
        R.drawable.ic_clear
    }