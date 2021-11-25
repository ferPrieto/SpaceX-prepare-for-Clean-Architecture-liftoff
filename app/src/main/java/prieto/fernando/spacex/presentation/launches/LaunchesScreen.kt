package prieto.fernando.spacex.presentation.launches

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import prieto.fernando.spacex.R
import prieto.fernando.spacex.presentation.theme.Dark
import prieto.fernando.spacex.presentation.theme.Light
import prieto.fernando.spacex.presentation.theme.SpaceXTypography

@ExperimentalMaterialApi
@Composable
fun LaunchesScreen(
    state: LaunchesContract.State,
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    val loadingComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
    val loadingProgress by animateLottieCompositionAsState(loadingComposition)

    val errorComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_conection))
    val errorProgress by animateLottieCompositionAsState(errorComposition)

    val bodyComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.rocket_launched))
    val bodyProgress by animateLottieCompositionAsState(
        bodyComposition,
        restartOnPlay = false
    )

    val openDialog = remember { mutableStateOf(false) }

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
            modifier = Modifier.padding(top = 16.dp, start = 16.dp),
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
                if (bodyProgress == 1f) {
                    FilterIcon(
                        modifier = Modifier
                            .padding(end = 8.dp, bottom = 8.dp)
                            .align(Alignment.End),
                        onClick = { openDialog.value = true }
                    )
                    LaunchesList(launchesItems = state.launches) { links ->
                        coroutineScope.launch {
                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            } else {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                    }

                } else {
                    LottieAnimation(
                        bodyComposition,
                        bodyProgress
                    )
                }
            }
        }
        if (openDialog.value) {
            FilterDialog(openDialog = openDialog)
        }
    }
}

@Composable
fun FilterDialog(openDialog: MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
            openDialog.value = false
        },
        title = {
            Text(text = "Dialog Title")
        },
        text = {
            Text("Here is a text ")
        },
        confirmButton = {
            Button(

                onClick = {
                    openDialog.value = false
                }) {
                Text("This is the Confirm Button")
            }
        },
        dismissButton = {
            Button(

                onClick = {
                    openDialog.value = false
                }) {
                Text("This is the dismiss Button")
            }
        }
    )
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
        Row(Modifier.animateContentSize()) {
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
private fun FilterIcon(modifier: Modifier, onClick: () -> Unit) {
    Box(modifier) {
        IconButton(
            onClick = onClick
        ) {
            Image(
                painter = painterResource(R.drawable.ic_filter),
                contentDescription = "Success or Failure launch Icon"
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