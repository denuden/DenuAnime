package com.gmail.denuelle42.denuanime.ui.common.chips

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Genre

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenreChips(modifier: Modifier = Modifier, genres : List<Genre>, color: Color = MaterialTheme.colorScheme.onPrimaryContainer) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        genres.forEach { genre ->
            if (genre.name.orEmpty().isNotEmpty()) {
                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            genre.name.orEmpty(),
                            color = color,
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 12.sp,
                            maxLines = 1,
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    border = BorderStroke(width = 0.dp, color = Color.White),
                    modifier = Modifier.heightIn(min = 24.dp, max = 28.dp)
                )
            }
        }
    }
}