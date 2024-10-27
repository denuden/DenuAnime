package com.gmail.denuelle42.denuanime.ui.common

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.request.crossfade
import coil3.toBitmap
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Genre
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("ResourceAsColor")
@Composable
fun DetailedAnimeItemCard(modifier: Modifier = Modifier, animeDetails: AnimeDetails) {
    var backgroundColor by remember { mutableIntStateOf(0) }
    // Animate the background color
    val animatedColor by animateColorAsState(
        targetValue = Color(backgroundColor),
        label = "Background Color"
    )

    val gradientColors: List<Color> = listOf(
        Color.Transparent,
        Color(0xFF000000)
    )

    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = animatedColor,
        ),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(animeDetails.images?.jpg?.large_image_url)
                    .allowHardware(false)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.baseline_image_24),
                contentDescription = stringResource(R.string.anime_banner),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.baseline_image_not_supported_24),
                onSuccess = { result ->
                    val bitmap = result.result.image.toBitmap()
                    Palette.Builder(bitmap).generate { palette ->
                        // Consume the palette.
                        backgroundColor =
                            palette?.getDominantColor(R.color.black) ?: R.color.black
                    }
                },
                modifier = Modifier.matchParentSize()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = gradientColors,
                        )
                    )
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart)
            ) {
                animeDetails.genres?.forEach { genre ->
                    if (genre.name.orEmpty().isNotEmpty()) {
                        AssistChip(
                            onClick = {},
                            label = {
                                Text(
                                    genre.name.orEmpty(),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    style = MaterialTheme.typography.labelSmall,
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

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = animeDetails.title ?: stringResource(R.string.nondescript),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "4 Season, 24 eps",
                        color = Color.White.copy(alpha = 0.75f),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                    Text(
                        text = "Finished Airing",
                        color = Color.White.copy(alpha = 0.75f),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                    Text(
                        text = "2019, Winter",
                        color = Color.White.copy(alpha = 0.75f),
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Rating: 8.4",
                        color = Color.White.copy(alpha = 0.75f),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                    Text(
                        text = "Rank 192",
                        color = Color.White.copy(alpha = 0.75f),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                    Text(
                        text = "PG - 13",
                        color = Color.White.copy(alpha = 0.75f),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun DetailedAnimeItemCardPreview() {
    DenuAnimeTheme {
        Surface(color = MaterialTheme.colorScheme.surface) {
            DetailedAnimeItemCard(
                modifier = Modifier,

                animeDetails = AnimeDetails(
                    title = "gfweklkl jklf mklw mklwdcmklcdw mklwe", genres = listOf(
                        Genre(name = "Sci-fi"),
                        Genre(name = "Adventure"),
                        Genre(name = "Drama"),
                        Genre(name = "Suspense"),
                        Genre(name = "Suspense"),
                        Genre(name = "Suspense"),
                    )
                )
            )
        }
    }
}