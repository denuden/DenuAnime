package com.gmail.denuelle42.denuanime.ui.anime.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Genre
import com.gmail.denuelle42.denuanime.ui.anime.search.AnimeSearchScreenEvents
import com.gmail.denuelle42.denuanime.ui.anime.search.AnimeSearchScreenState
import com.gmail.denuelle42.denuanime.ui.common.CustomSwitch
import com.gmail.denuelle42.denuanime.ui.common.chips.GenreFilterChip
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.CoroutineHelper
import com.gmail.denuelle42.denuanime.utils.clickableDelayed
import com.gmail.denuelle42.denuanime.utils.formatTimestampAsDashedLongDate
import java.util.Locale

@Composable
fun FullSearchFilters(
    modifier: Modifier = Modifier,
    animeSearchScreenState: AnimeSearchScreenState,
    onTriggerSearch : () -> Unit,
    onEvent: (AnimeSearchScreenEvents) -> Unit,
) {
    FullSearchFiltersContent(
        modifier = modifier,
        state = animeSearchScreenState,
        onTriggerSearch = onTriggerSearch,
        onEvent
    )
}


@Composable
fun FullSearchFiltersContent(
    modifier: Modifier = Modifier,
    state: AnimeSearchScreenState,
    onTriggerSearch : () -> Unit,
    onEvent: (AnimeSearchScreenEvents) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollState)) {
        //=============== Type
        TypeFilter(
            selectedType = state.typeFilter.orEmpty(),
            onSelectType = { type ->
                onEvent(AnimeSearchScreenEvents.OnSetLoadingSearchAnime)
                onEvent(AnimeSearchScreenEvents.OnChangeTypeFilter(type))
                onTriggerSearch()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        //===================== score
        ScoreFilter(
            onFixedSliderChange = { score -> },
            onRangeSliderChange = { start, end -> }
        )
        Spacer(modifier = Modifier.height(16.dp))
        //=============== status
        StatusFilter(onSelectStatus = { status -> })
        Spacer(modifier = Modifier.height(16.dp))

        //=============== rating
        RatingFilter(onSelectRating = { rating -> })
        Spacer(modifier = Modifier.height(16.dp))

        //=============== is SFW
        SFWFilter { }
        Spacer(modifier = Modifier.height(16.dp))

        //=============== genre
        GenreFilter()
        Spacer(modifier = Modifier.height(16.dp))

        //=============== order
        OrderByFilter(onSelectOrder = { order -> })
        Spacer(modifier = Modifier.height(16.dp))

        //=============== sort
        SortFilter(onSelectSort = { sort -> })
        Spacer(modifier = Modifier.height(16.dp))

        val context = LocalContext.current

        //=============== Start Date
        var showStartDateDialog by remember { mutableStateOf(false) }
        StartDateFilter(
            onSelectedDate = { date ->
                if (date != null) {
                    Toast.makeText(
                        context,
                        formatTimestampAsDashedLongDate(date),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            },
            showDialog = showStartDateDialog,
            onDismiss = { showStartDateDialog = false },
            onShowDialog = { showStartDateDialog = true },
            context = context
        )
        Spacer(modifier = Modifier.height(16.dp))

        //================= End Date
        var showEndDateDialog by remember { mutableStateOf(false) }
        EndDateFilter(
            onSelectedDate = { date ->
                if (date != null) {
                    Toast.makeText(
                        context,
                        formatTimestampAsDashedLongDate(date),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            },
            showDialog = showEndDateDialog,
            onDismiss = { showEndDateDialog = false },
            onShowDialog = { showEndDateDialog = true },
            context = context
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TypeFilter(
    modifier: Modifier = Modifier,
    selectedType: String,
    onSelectType: (String) -> Unit
) {
    val listOfType = listOf(
        stringResource(R.string.type_tv),
        stringResource(R.string.type_movie),
        stringResource(R.string.type_ova),
        stringResource(R.string.type_special),
        stringResource(R.string.type_ona),
        stringResource(R.string.type_music),
        stringResource(R.string.type_cm),
        stringResource(R.string.type_pv),
        stringResource(R.string.type_tv_special)
    )
    var selectedIndex by remember { mutableIntStateOf(0) }
    selectedIndex = listOfType.indexOf(selectedType)
    Column(modifier = modifier) {
        Title(title = stringResource(R.string.label_type), onClickInformation = { })
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOfType.forEachIndexed { index, type ->
                FilterChip(
                    selected = selectedIndex == index,
                    onClick = {
                        selectedIndex = index
                        onSelectType(listOfType[selectedIndex])
                    },
                    label = { Text(type) },
                )
            }
        }
    }
}

@Composable
fun ScoreFilter(
    modifier: Modifier = Modifier,
    onFixedSliderChange: (String) -> Unit,
    onRangeSliderChange: (String, String) -> Unit
) {
    var fixedScoreSliderPosition by remember { mutableFloatStateOf(0f) }
    var rangeScoreSliderPosition by remember { mutableStateOf(0f..10f) }
    var isScoreFixed by remember { mutableStateOf(true) }
    Column {
        Title(title = stringResource(R.string.label_score), onClickInformation = { })
        Row(
            modifier = modifier.padding(top = 6.dp)
        ) {
            Column {
                Text(
                    stringResource(R.string.label_fixed_score),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray
                )
                CustomSwitch(
                    checked = isScoreFixed,
                    onCheckedChange = { isScoreFixed = it }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = if (isScoreFixed) String.format(
                        Locale.US,
                        "%.2f",
                        fixedScoreSliderPosition
                    ) else "${
                        String.format(
                            Locale.US,
                            "%.2f",
                            rangeScoreSliderPosition.start
                        )
                    } - ${
                        String.format(
                            Locale.US,
                            "%.2f",
                            rangeScoreSliderPosition.endInclusive
                        )
                    }",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray
                )
                if (isScoreFixed) {
                    Slider(
                        value = fixedScoreSliderPosition,
                        onValueChange = {
                            fixedScoreSliderPosition = it
                        },
                        valueRange = 0f..10f,
                        onValueChangeFinished = {
                            onFixedSliderChange(
                                String.format(
                                    Locale.US,
                                    "%.2f",
                                    fixedScoreSliderPosition
                                )
                            )
                        }
                    )
                } else {
                    RangeSlider(
                        value = rangeScoreSliderPosition,
                        onValueChange = { range -> rangeScoreSliderPosition = range },
                        valueRange = 0f..10f,
                        onValueChangeFinished = {
                            onRangeSliderChange(
                                String.format(
                                    Locale.US,
                                    "%.2f",
                                    rangeScoreSliderPosition.start
                                ), String.format(
                                    Locale.US,
                                    "%.2f",
                                    rangeScoreSliderPosition.endInclusive
                                )
                            )
                        },
                    )
                }
            }
        }
    }

}

@Composable
fun StatusFilter(modifier: Modifier = Modifier, onSelectStatus: (String) -> Unit) {
    val radioOptions = listOf(
        stringResource(R.string.status_airing),
        stringResource(R.string.status_completed), stringResource(R.string.status_upcoming)
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Column(modifier = modifier) {
        Title(title = stringResource(R.string.label_status), onClickInformation = { })
        Column(
            Modifier
                .selectableGroup()
                .padding(top = 6.dp)
        ) {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                                onSelectStatus(text)
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = null // null recommended for accessibility with screen readers
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RatingFilter(modifier: Modifier = Modifier, onSelectRating: (String) -> Unit) {
    val listOfRatings = listOf(
        stringResource(R.string.rating_g_all_ages),
        stringResource(R.string.rating_pg_children),
        stringResource(R.string.rating_pg_13_teens_13_or_older),
        stringResource(R.string.rating_r_17_violence_profanity),
        stringResource(R.string.rating_r_mild_nudity),
        stringResource(R.string.rating_rx_hentai)
    )
    var selectedRating by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()
    Column(modifier = modifier) {
        Title(title = stringResource(R.string.label_rating), onClickInformation = { })
        FlowRow(
            maxLines = 1,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(top = 6.dp),
        ) {
            listOfRatings.forEachIndexed { index, rating ->
                FilterChip(
                    selected = selectedRating == index,
                    onClick = {
                        selectedRating = index
                        onSelectRating(listOfRatings[selectedRating])
                    },
                    label = { Text(rating) },
                )
            }
        }
    }
}

@Composable
fun SFWFilter(modifier: Modifier = Modifier, onChange: (Boolean) -> Unit) {
    var isSfw by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        Title(title = stringResource(R.string.label_sfw), onClickInformation = { })
        CustomSwitch(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 64.dp, top = 6.dp), // Align switch to center of Box
            checked = isSfw,
            onCheckedChange = {
                isSfw = it
                onChange(isSfw)
            }
        )
    }
}

@Composable
fun GenreFilter(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        Title(title = stringResource(R.string.label_genre), onClickInformation = {})
        GenreFilterChip(
            categoryList = listOf(
                Genre(name = "Action"),
                Genre(name = "Romance"),
                Genre(name = "Comedy"),
                Genre(name = "Comedy"),
            ),
            onSelectedCategory = { genre ->

            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrderByFilter(modifier: Modifier = Modifier, onSelectOrder: (String) -> Unit) {
    val listOfRatings = listOf(
        stringResource(R.string.orderby_title),
        stringResource(R.string.orderby_released_date),
        stringResource(R.string.orderby_end_date),
        stringResource(R.string.orderby_episode_count),
        stringResource(R.string.orderby_score),
        stringResource(R.string.orderby_rank),
        stringResource(R.string.orderby_popularity),
    )
    var selectedOrder by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()
    Column(modifier = modifier) {
        Title(title = stringResource(R.string.label_order_by), onClickInformation = { })
        FlowRow(
            maxLines = 1,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(top = 6.dp),
        ) {
            listOfRatings.forEachIndexed { index, order ->
                FilterChip(
                    selected = selectedOrder == index,
                    onClick = {
                        selectedOrder = index
                        onSelectOrder(listOfRatings[selectedOrder])
                    },
                    label = { Text(order) },
                )
            }
        }
    }
}

@Composable
fun SortFilter(modifier: Modifier = Modifier, onSelectSort: (String) -> Unit) {
    val radioOptions = listOf(
        stringResource(R.string.sort_ascending),
        stringResource(R.string.sort_descending), stringResource(
            R.string.sort_none
        )
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Column(modifier = modifier) {
        Title(title = stringResource(R.string.label_sort), onClickInformation = { })
        Column(
            Modifier
                .selectableGroup()
                .padding(top = 6.dp)
        ) {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                                onSelectSort(text)
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = null // null recommended for accessibility with screen readers
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StartDateFilter(
    modifier: Modifier = Modifier,
    onSelectedDate: (Long?) -> Unit,
    onDismiss: () -> Unit,
    showDialog: Boolean = false,
    onShowDialog: () -> Unit,
    context: Context,
) {
    val datePickerState = rememberDatePickerState()
    val date =
        if (datePickerState.selectedDateMillis == null) stringResource(R.string.label_select_start_date) else formatTimestampAsDashedLongDate(
            datePickerState.selectedDateMillis!!
        )
    Box(modifier = modifier) {
        Title(title = date, onClickInformation = { }, modifier = Modifier.clickableDelayed {
            onShowDialog()
        })

        if (showDialog) {
            DatePickerDialog(
                onDismissRequest = { },
                confirmButton = {
                    TextButton(onClick = {
                        if (datePickerState.selectedDateMillis == null) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.text_please_select_a_date),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@TextButton
                        }
                        onSelectedDate(datePickerState.selectedDateMillis)
                        onDismiss()
                    }) {
                        Text(stringResource(R.string.btn_ok))
                    }
                },
                dismissButton = {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(R.string.btn_cancel))
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EndDateFilter(
    modifier: Modifier = Modifier,
    onSelectedDate: (Long?) -> Unit,
    onDismiss: () -> Unit,
    showDialog: Boolean = false,
    onShowDialog: () -> Unit,
    context: Context,
) {
    val datePickerState = rememberDatePickerState()
    val date =
        if (datePickerState.selectedDateMillis == null) stringResource(R.string.label_select_end_date) else formatTimestampAsDashedLongDate(
            datePickerState.selectedDateMillis!!
        )
    Box(modifier = modifier) {
        Title(title = date, onClickInformation = { }, modifier = Modifier.clickableDelayed {
            onShowDialog()
        })

        if (showDialog) {
            DatePickerDialog(
                onDismissRequest = { },
                confirmButton = {
                    TextButton(onClick = {
                        if (datePickerState.selectedDateMillis == null) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.text_please_select_a_date),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@TextButton
                        }
                        onSelectedDate(datePickerState.selectedDateMillis)
                        onDismiss()
                    }) {
                        Text(stringResource(R.string.btn_ok))
                    }
                },
                dismissButton = {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(R.string.btn_cancel))
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@Composable
private fun Title(modifier: Modifier = Modifier, onClickInformation: () -> Unit, title: String) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .padding(bottom = 6.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Color.Gray)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .clip(MaterialTheme.shapes.small)
                .background(color = Color.LightGray.copy(alpha = 0.3f))
                .padding(horizontal = 16.dp)
        ) {
            Text(
                title,
                modifier = Modifier
                    .weight(1f)
            )
            IconButton(onClick = onClickInformation) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                )
            }
        }
    }

}

@Preview
@Composable
private fun FullSearchFiltersPreview() {
    DenuAnimeTheme {
        Surface(modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant)) {
            FullSearchFiltersContent(
                Modifier.padding(16.dp),
                state = AnimeSearchScreenState(),
                onTriggerSearch = {},
                onEvent = {})
        }
    }
}