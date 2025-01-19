package com.gmail.denuelle42.denuanime.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.data.remote.models.episodes.Episode
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.AsyncImageWithErrorHandler

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnimeListItemCard(
    modifier: Modifier = Modifier,
    animeDetails: AnimeDetails,
    recentEpisodesList: List<Episode> = emptyList(),
    onClick : (Int) -> Unit

) {
    OutlinedCard(
        onClick = {
            onClick(animeDetails.mal_id ?: 0)
        },
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(6.dp)
        ) {

            AsyncImageWithErrorHandler(
                model = animeDetails.images?.jpg?.large_image_url,
                shouldShowEnlargeButton = false,
                modifier = Modifier .padding(end = 8.dp)
                    .size(width = 74.dp, height = 96.dp)
                    .clip(MaterialTheme.shapes.small)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {

                Text(
                    text = animeDetails.title.orEmpty()
                        .ifEmpty { stringResource(R.string.no_title_specified) },
                    style = MaterialTheme.typography.titleSmall)

                if(recentEpisodesList.isNotEmpty()){
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        recentEpisodesList.forEach { episode ->
                            TextButton(
                                onClick = {},
                                contentPadding = PaddingValues(vertical = 3.dp, horizontal = 10.dp),
                                modifier = Modifier.height(28.dp),
                            ) {
                                    Text(text = episode.title ?: "Unknown Episode")
                            }
                        }
                    }
                }

            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                tint = Color.LightGray
            )
        }
    }
}

@Preview
@Composable
private fun AnimeListItemCardPreview() {
    DenuAnimeTheme {
        AnimeListItemCard(
            animeDetails = AnimeDetails(

            ),
            recentEpisodesList = listOf(
                Episode(
                    title = "Episode 1"
                ),
                Episode(
                    title = "Episode 2"
                ),

            )
        ){}
    }
}