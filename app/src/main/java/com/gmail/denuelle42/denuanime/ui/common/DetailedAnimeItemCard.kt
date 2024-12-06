package com.gmail.denuelle42.denuanime.ui.common

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Genre
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.AsyncImageWithBackgroundPalette

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
        shape = RectangleShape,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            AsyncImageWithBackgroundPalette(
              model =  animeDetails.images?.jpg?.large_image_url,
                onPaletteBuilderSuccess = { backgroundColor = it},
                shouldShowEnlargeButton = false,
                contentScale = ContentScale.Crop,
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

           GenreChips(genres = animeDetails.genres.orEmpty(), modifier = Modifier.align(Alignment.TopStart).padding(12.dp))

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
                        text = "${animeDetails.episodes ?: "Unknown"} eps",
                        color = Color.White.copy(alpha = 0.75f),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                    Text(
                        text = animeDetails.status.orEmpty()
                            .ifEmpty { "Unknown" },
                        color = Color.White.copy(alpha = 0.75f),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                    Text(
                        text = "${animeDetails.year ?: "0000"}, ${animeDetails.season ?: "---"}",
                        color = Color.White.copy(alpha = 0.75f),
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Score: ${animeDetails.score ?: "--"}",
                        color = Color.White.copy(alpha = 0.75f),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                    Text(
                        text = "Rank ${animeDetails.rank ?: "--" }",
                        color = Color.White.copy(alpha = 0.75f),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                    Text(
                        text = animeDetails.rating.orEmpty().ifEmpty { "Unknown Rating" },
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