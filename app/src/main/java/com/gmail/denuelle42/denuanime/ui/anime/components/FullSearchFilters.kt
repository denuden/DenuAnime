package com.gmail.denuelle42.denuanime.ui.anime.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FullSearchFilters(modifier: Modifier = Modifier) {
    val listOfType = listOf(
        "TV",
        "Movie",
        "OVA",
        "Special",
        "ONA",
        "Music",
        "CM",
        "PV",
        "TV Special"
    )

    var selectedType by remember { mutableIntStateOf(0) }

    var fixedScoreSliderPosition by remember { mutableFloatStateOf(0f) }
    var rangeScoreSliderPosition by remember { mutableStateOf(0f..10f) }
    var isScoreFixed by remember { mutableStateOf(true) }
    //=============== Type
    Column(modifier = modifier) {
        Title(title = "Type", onClickInformation = { })
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOfType.forEachIndexed { index, type ->
                FilterChip(
                    selected = selectedType == index,
                    onClick = { selectedType = index },
                    label = { Text(type) }
                )
            }
        }

        Spacer(modifier = Modifier.heightIn(16.dp))
        //===================== score
        Title(title = "Score", onClickInformation = { })
        Row(
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Column {
                Text("Fixed Score", style = MaterialTheme.typography.labelLarge, color = Color.Gray)
                Switch(
                    checked = isScoreFixed,
                    thumbContent = if (isScoreFixed) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                            )
                        }
                    } else {
                        null
                    },
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
                        onValueChange = { fixedScoreSliderPosition = it },
                        valueRange = 0f..10f
                    )
                } else {
                    RangeSlider(
                        value = rangeScoreSliderPosition,
                        onValueChange = { range -> rangeScoreSliderPosition = range },
                        valueRange = 0f..10f,
                        onValueChangeFinished = {
                            // launch some business logic update with the state you hold
                            // viewModel.updateSelectedSliderValue(sliderPosition)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun Title(modifier: Modifier = Modifier, onClickInformation: () -> Unit, title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(color = Color.LightGray.copy(alpha = 0.3f))
            .padding(vertical = 8.dp, horizontal = 16.dp)
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

@Preview
@Composable
private fun FullSearchFiltersPreview() {
    DenuAnimeTheme {
        Surface(modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant)) {
            FullSearchFilters(Modifier.padding(16.dp))
        }
    }
}