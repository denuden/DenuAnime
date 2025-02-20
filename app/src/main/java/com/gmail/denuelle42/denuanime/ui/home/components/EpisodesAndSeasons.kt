package com.gmail.denuelle42.denuanime.ui.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@Composable
fun EpisodesAndSeasonsTab(
    modifier: Modifier = Modifier,
    state: Int,
    isEnabled : Boolean = true,
    onSelectTab: (Int) -> Unit
){
    val titles = listOf(stringResource(R.string.label_recent_episodes_wnewline),
        stringResource(R.string.label_ongoing_seasons_wnewline),
        stringResource(R.string.label_upcoming_seasons_wnewline)
    )

    Column(
        modifier = modifier.clip(MaterialTheme.shapes.extraSmall)
    ) {
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            titles.forEachIndexed { index, title ->
                SegmentedButton(
                    enabled = isEnabled,
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = titles.size,
                        baseShape = MaterialTheme.shapes.small
                    ),
                    onClick = {
                        onSelectTab(index)
                      },
                    selected = index == state,
                    border = BorderStroke(width = 1.dp, color = Color.Gray),
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(title)
                }
            }
        }
    }
}


@Preview
@Composable
private fun EpisodesAndSeasonsPreview() {
    DenuAnimeTheme {
        EpisodesAndSeasonsTab(state =0
        ){}
    }
}