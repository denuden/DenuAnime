package com.gmail.denuelle42.denuanime.ui.common.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.AsyncImageAvatarWithErrorHandler

@Composable
fun DetailedAnimeListItemCard(
    modifier: Modifier = Modifier,
    animeDetails : AnimeDetails
) {
    DetailedAnimeListItemCardContent(modifier = modifier, data = animeDetails)
}
@Composable
fun DetailedAnimeListItemCardContent(
    modifier: Modifier = Modifier,
    data: AnimeDetails

) {
    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.small).padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImageAvatarWithErrorHandler(
                model = "${data.images?.jpg?.small_image_url}",
                shouldShowEnlargeButton = false,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .padding(end = 8.dp)
            )
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = data.title_english.orEmpty().ifEmpty { "No Title" },
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.weight(1f)
                    )
                    AssistChip(
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(end = 2.dp)
                                        .size(12.dp)
                                )
                                Text("${data.score}", style = MaterialTheme.typography.labelSmall)
                            }
                        },
                        onClick = { },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier
                            .height(24.dp)
                    )
                }
                Text(
                    text = "${data.season.orEmpty().uppercase()} ${data.year} | ${data.status.orEmpty().uppercase()}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Column(
            modifier
                .padding(8.dp)
                .background(
                    MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.small
                )
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Rank: ${data.rank}", style = MaterialTheme.typography.labelMedium)
                    Text("Popularity: ${data.popularity}", style = MaterialTheme.typography.labelMedium)
                }
                VerticalDivider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(2.dp),
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("${data.episodes} Episodes", style = MaterialTheme.typography.labelMedium)
                    Text("${data.duration}", style = MaterialTheme.typography.labelMedium)
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .background(color = MaterialTheme.colorScheme.surfaceContainer, shape = MaterialTheme.shapes.small)
                    .padding(vertical = 8.dp, horizontal = 4.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "${data.rating}".uppercase(),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Preview
@Composable
private fun DetailedAnimeListItemCardPreview() {
    DenuAnimeTheme {
        Surface(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)
        ) {
            DetailedAnimeListItemCardContent(
                data = AnimeDetails()
            )
        }
    }
}