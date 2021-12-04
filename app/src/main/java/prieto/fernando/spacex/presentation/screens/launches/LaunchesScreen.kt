package prieto.fernando.spacex.presentation.screens.launches

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import prieto.fernando.spacex.R
import prieto.fernando.spacex.theme.Dark
import prieto.fernando.spacex.theme.Light
import prieto.fernando.spacex.theme.SpaceXTypography
import prieto.fernando.spacex.presentation.screens.common.ErrorAnimation

@InternalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun LaunchesScreen(
    state: LaunchesContract.State,
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    onEventSent: (event: LaunchesContract.Event) -> Unit,
    effectFlow: Flow<LaunchesContract.Effect>?,
    onClickableLinkRetrieved: (clickableLinkEffect: LaunchesContract.Effect.ClickableLink) -> Unit,
    onLinkClicked: (clickableLinkEffect: LaunchesContract.Effect.LinkClicked) -> Unit
) {
    val loadingComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
    val loadingProgress by animateLottieCompositionAsState(loadingComposition)

    val errorComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_conection))
    val errorProgress by animateLottieCompositionAsState(errorComposition)

    val noResultsComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_results_animation))
    val noResultsProgress by animateLottieCompositionAsState(noResultsComposition)

    val bodyComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.rocket_launched))
    val bodyProgress by animateLottieCompositionAsState(
        bodyComposition,
        restartOnPlay = false
    )

    val openDialog = remember { mutableStateOf(false) }
    val orderChecked = remember { mutableStateOf(false) }

    EffectsListener(effectFlow, onClickableLinkRetrieved, onLinkClicked)

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
            state.isError -> { ErrorAnimation(errorComposition, errorProgress) }
            else -> {
                if (bodyProgress == 1f) {
                    FilterIcon(
                        modifier = Modifier
                            .padding(end = 8.dp, bottom = 8.dp)
                            .align(Alignment.End),
                        onClick = { openDialog.value = true }
                    )
                    if (state.launchUiModels.isNotEmpty()) {
                        LaunchesList(
                            launchesItems = state.launchUiModels,
                            onEventSent = onEventSent,
                            coroutineScope = coroutineScope,
                            bottomSheetScaffoldState = bottomSheetScaffoldState
                        )
                    } else {
                        NoResultsFeedback(noResultsComposition, noResultsProgress)
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
            FilterDialog(
                openDialog = openDialog,
                orderChecked = orderChecked,
                onEventSent = onEventSent
            )
        }
    }
}

@Composable
private fun EffectsListener(
    effectFlow: Flow<LaunchesContract.Effect>?,
    onClickableLinkRetrieved: (clickableLinkEffect: LaunchesContract.Effect.ClickableLink) -> Unit,
    onLinkClicked: (clickableLinkEffect: LaunchesContract.Effect.LinkClicked) -> Unit
) {
    LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is LaunchesContract.Effect.ClickableLink -> onClickableLinkRetrieved(effect)
                is LaunchesContract.Effect.LinkClicked -> onLinkClicked(effect)
            }
        }?.collect()
    }
}

@Composable
private fun NoResultsFeedback(
    noResultsComposition: LottieComposition?,
    noResultsProgress: Float
) {
    Box {
        Text(
            text = stringResource(id = R.string.launches_no_results_found),
            style = SpaceXTypography.h3,
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.TopCenter),
            color = if (MaterialTheme.colors.isLight) Light.Accent
            else Dark.Accent
        )
        LottieAnimation(
            composition = noResultsComposition,
            progress = noResultsProgress,
            alignment = Alignment.Center
        )
    }
}

@Composable
fun FilterDialog(
    openDialog: MutableState<Boolean>,
    orderChecked: MutableState<Boolean>,
    onEventSent: (event: LaunchesContract.Event) -> Unit
) {
    var textState by remember { mutableStateOf("") }
    val maxYearLength = 4

    AlertDialog(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = if (MaterialTheme.colors.isLight) Light.DialogWindowBackground else Dark.DialogWindowBackground,
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(
                text = stringResource(id = R.string.dialog_title),
                style = SpaceXTypography.h2,
                color = if (MaterialTheme.colors.isLight) Light.TextColorPrimary else Dark.TextColorPrimary
            )
        },
        text = {
            Row {
                OutlinedTextField(
                    value = textState,
                    modifier = Modifier
                        .width(180.dp)
                        .padding(end = 24.dp),

                    onValueChange = { if (it.length <= maxYearLength) textState = it },
                    label = { Text(stringResource(id = R.string.dialog_year)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (MaterialTheme.colors.isLight) Light.Accent else Dark.Accent,
                        unfocusedBorderColor = if (MaterialTheme.colors.isLight) Light.TextColorSecondary else Dark.TextColorSecondary,
                        textColor = if (MaterialTheme.colors.isLight) Light.TextColorPrimary else Dark.TextColorPrimary,
                        disabledTextColor = if (MaterialTheme.colors.isLight) Light.TextColorSecondary else Dark.TextColorSecondary,
                        cursorColor = if (MaterialTheme.colors.isLight) Light.Accent else Dark.Accent,
                        focusedLabelColor = if (MaterialTheme.colors.isLight) Light.Accent else Dark.Accent
                    )
                )
                Column {
                    Text(
                        text = stringResource(id = R.string.dialog_order_criteria),
                        style = SpaceXTypography.body1,
                        color = if (MaterialTheme.colors.isLight) Light.TextColorPrimary else Dark.TextColorPrimary
                    )
                    Switch(
                        checked = orderChecked.value,
                        onCheckedChange = { orderChecked.value = it },
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = if (MaterialTheme.colors.isLight) Light.Accent else Dark.Accent,
                            checkedThumbColor = if (MaterialTheme.colors.isLight) Light.Accent else Dark.Accent,
                            uncheckedTrackColor = if (MaterialTheme.colors.isLight) Light.TextColorSecondary else Dark.TextColorSecondary,
                            uncheckedThumbColor = if (MaterialTheme.colors.isLight) Light.TextColorSecondary else Dark.TextColorSecondary
                        )
                    )
                }
            }
        },
        confirmButton = { ConfirmButton(openDialog, onEventSent, textState, orderChecked) },
        dismissButton = { DismissButton(openDialog) }
    )
}

@Composable
private fun DismissButton(openDialog: MutableState<Boolean>) {
    TextButton(
        onClick = {
            openDialog.value = false
        }) {
        Text(
            text = stringResource(id = R.string.dialog_cancel_button),
            style = SpaceXTypography.subtitle2,
            color = if (MaterialTheme.colors.isLight) Light.TextColorSecondary else Dark.TextColorSecondary
        )
    }
}

@Composable
private fun ConfirmButton(
    openDialog: MutableState<Boolean>,
    onEventSent: (event: LaunchesContract.Event) -> Unit,
    textState: String,
    orderChecked: MutableState<Boolean>
) {
    TextButton(
        onClick = {
            openDialog.value = false
            onEventSent(LaunchesContract.Event.Filter(textState, orderChecked.value))
        }) {
        Text(
            stringResource(id = R.string.dialog_ok_button),
            style = SpaceXTypography.subtitle2,
            color = if (MaterialTheme.colors.isLight) Light.Accent else Dark.Accent
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun LaunchesList(
    launchesItems: List<LaunchUiModel>,
    onEventSent: (event: LaunchesContract.Event) -> Unit,
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(launchesItems) { item ->
            LaunchItemRow(
                launchUiModelItem = item,
                onEventSent = onEventSent,
                coroutineScope = coroutineScope,
                bottomSheetScaffoldState = bottomSheetScaffoldState
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun LaunchItemRow(
    launchUiModelItem: LaunchUiModel,
    onEventSent: (event: LaunchesContract.Event) -> Unit,
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = if (MaterialTheme.colors.isLight) Light.ItemBackground
        else Dark.ItemBackground,
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                coroutineScope.launch {
                    if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    } else {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
                onEventSent(LaunchesContract.Event.ClickableLinks(launchUiModelItem.linksUiModel))
            }
    ) {
        Row(Modifier.animateContentSize()) {
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(start = 8.dp)
            ) {
                LaunchItemThumbnail(launchUiModelItem.linksUiModel.missionPatchSmall)
            }
            LaunchItemTags(
                item = launchUiModelItem,
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
                launchUiModelItem = launchUiModelItem,
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
                launchUiModelItem = launchUiModelItem,
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
    launchUiModelItem: LaunchUiModel,
    modifier: Modifier
) {
    Box(modifier) {
        Image(
            painter = painterResource(getSuccessDrawable(launchUiModelItem.launchSuccess)),
            contentDescription = "Success or Failure launch Icon"
        )
    }
}

@Composable
private fun LaunchItemContent(
    launchUiModelItem: LaunchUiModel,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        LaunchItemContentText(launchUiModelItem.missionName)
        LaunchItemContentText(launchUiModelItem.launchDate)
        LaunchItemContentText("${launchUiModelItem.rocketUiModel.rocketName}/${launchUiModelItem.rocketUiModel.rocketType}")
        LaunchItemContentText("+/-${launchUiModelItem.differenceOfDays}")
    }
}

@Composable
fun LaunchItemThumbnail(thumbnailUrl: String) {
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
    item: LaunchUiModel,
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