package com.gmail.denuelle42.denuanime.ui.anime.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

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
    //=============== Type
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .background(color = Color.LightGray.copy(alpha = 0.3f))
                .padding(8.dp)
        ) {
            Text(
                "Type",
                modifier = Modifier
                    .weight(1f)
            )
            IconButton(onClick = {

            }) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                )
            }
        }
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

        //===================== score

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