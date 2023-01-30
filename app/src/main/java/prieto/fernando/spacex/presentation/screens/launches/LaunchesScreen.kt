package prieto.fernando.spacex.presentation.screens.launches

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
import prieto.fernando.spacex.presentation.screens.error.ErrorAnimation
import prieto.fernando.spacex.theme.SpaceX.LocalColors
import prieto.fernando.spacex.theme.SpaceX as SpaceX1

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
    onLinkClicked: (clickableLinkEffect: LaunchesContract.Effect.LinkClicked) -> Unit,
    modifier: Modifier = Modifier
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
        modifier = modifier
            .fillMaxHeight()
            .background(LocalColors.current.background)
    ) {
        Text(
            text = stringResource(id = R.string.launches_title),
            modifier = modifier.padding(top = 16.dp, start = 16.dp),
            style = SpaceX1.LocalTypography.current.h1,
            color = LocalColors.current.textColorPrimary
        )
        when {
            state.isLoading -> {
                LottieAnimation(
                    composition = loadingComposition,
                    progress = loadingProgress,
                    modifier = modifier.semantics { contentDescription = "Loading Animation" }
                )
            }
            state.isError -> {
                ErrorAnimation(errorComposition, errorProgress)
            }
            else -> {
                if (bodyProgress == 1f) {
                    FilterIcon(
                        modifier = modifier
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
                        composition = bodyComposition,
                        progress = bodyProgress,
                        modifier = modifier.semantics { contentDescription = "Launches Animation" }
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
            style = SpaceX1.LocalTypography.current.h3,
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.TopCenter),
            color = LocalColors.current.accent
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
        backgroundColor = LocalColors.current.dialogWindowBackground,
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(
                text = stringResource(id = R.string.dialog_title),
                style = SpaceX1.LocalTypography.current.h2,
                color = LocalColors.current.textColorPrimary
            )
        },
        text = {
            Row {
                OutlinedTextField(
                    value = textState,
                    modifier = Modifier
                        .width(180.dp)
                        .padding(end = 24.dp)
                        .semantics { contentDescription = "Year Selection" },

                    onValueChange = { if (it.length <= maxYearLength) textState = it },
                    label = { Text(stringResource(id = R.string.dialog_year)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = LocalColors.current.accent,
                        unfocusedBorderColor = LocalColors.current.textColorSecondary,
                        textColor = LocalColors.current.textColorPrimary,
                        disabledTextColor = LocalColors.current.textColorSecondary,
                        cursorColor = LocalColors.current.accent,
                        focusedLabelColor = LocalColors.current.accent
                    )
                )
                Column {
                    Text(
                        text = stringResource(id = R.string.dialog_order_criteria),
                        style = SpaceX1.LocalTypography.current.body1,
                        color = LocalColors.current.textColorPrimary
                    )
                    Switch(
                        checked = orderChecked.value,
                        onCheckedChange = { orderChecked.value = it },
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = LocalColors.current.accent,
                            checkedThumbColor = LocalColors.current.accent,
                            uncheckedTrackColor = LocalColors.current.textColorSecondary,
                            uncheckedThumbColor = LocalColors.current.textColorSecondary,
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
        }
    ) {
        Text(
            text = stringResource(id = R.string.dialog_cancel_button),
            style = SpaceX1.LocalTypography.current.subtitle2,
            color = LocalColors.current.textColorSecondary
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
        modifier = Modifier.semantics { contentDescription = "OK Dialog Button" },
        onClick = {
            openDialog.value = false
            onEventSent(LaunchesContract.Event.Filter(textState, orderChecked.value))
        }
    ) {
        Text(
            stringResource(id = R.string.dialog_ok_button),
            style = SpaceX1.LocalTypography.current.subtitle2,
            color = LocalColors.current.accent
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
        backgroundColor = LocalColors.current.itemBackground,
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .semantics { contentDescription = "Item-${launchUiModelItem.missionName}" }
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
                contentDescription = "Filter Button"
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
            painter = painterResource(launchUiModelItem.launchSuccess.successDrawable()),
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
        LaunchItemTagText(item.isPastLaunch.titleSinceOrFrom())
    }
}

@Composable
private fun LaunchItemTagText(stringResource: Int) {
    Text(
        text = stringResource(id = stringResource),
        textAlign = TextAlign.Start,
        style = SpaceX1.LocalTypography.current.subtitle1,
        maxLines = 1,
        color = LocalColors.current.textColorSecondary
    )
}

private fun Boolean.titleSinceOrFrom() = if (this) R.string.company_data_since else R.string.company_data_from

@Composable
private fun LaunchItemContentText(text: String) {
    Text(
        text = text,
        textAlign = TextAlign.End,
        style = SpaceX1.LocalTypography.current.subtitle2,
        maxLines = 1,
        color = LocalColors.current.textHighlighted
    )
}

private fun Boolean.successDrawable() = if (this) R.drawable.ic_check else R.drawable.ic_clear
