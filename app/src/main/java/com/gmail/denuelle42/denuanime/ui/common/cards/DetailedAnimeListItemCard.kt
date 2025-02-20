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
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.AsyncImageAvatarWithErrorHandler

@Composable
fun DetailedAnimeListItemCard(modifier: Modifier = Modifier) {
    DetailedAnimeListItemCardContent()
}
@Composable
fun DetailedAnimeListItemCardContent(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.small).padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImageAvatarWithErrorHandler(
                model = "https://cdn.dribbble.com/userupload/15382945/file/original-d075517ff1d6d72e7d57d69eca231090.png?resize=752x&vertical=center",
                shouldShowEnlargeButton = false,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .padding(end = 8.dp)
            )

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Kaguya Sama : Love is war",
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
                                Text("4.9", style = MaterialTheme.typography.labelSmall)
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
                    text = "SPRING 2013 | FINISHED AIRING",
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
                    Text("Rank: 119", style = MaterialTheme.typography.labelMedium)
                    Text("Popularity: 1", style = MaterialTheme.typography.labelMedium)
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
                    Text("25 Episodes", style = MaterialTheme.typography.labelMedium)
                    Text("24 min per ep", style = MaterialTheme.typography.labelMedium)
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
                    "R-17+ (Violence & Profanity)".uppercase(),
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
            DetailedAnimeListItemCardContent()
        }
    }
}