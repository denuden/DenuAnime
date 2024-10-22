package com.gmail.denuelle42.denuanime.ui.common

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

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
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
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
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
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

            AssistChip(
                onClick = {},
                label = { Text("Adventure", color = MaterialTheme.colorScheme.onPrimaryContainer) },
                colors = AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.align(Alignment.TopStart).padding(12.dp),
            )

            IconButton(
                onClick = {},
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.align(Alignment.TopEnd).padding(12.dp)
            ) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = stringResource(R.string.favorites), tint = Color.White)
            }
            Text(
                text = animeDetails.title ?: stringResource(R.string.nondescript),
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.BottomStart).padding(start = 16.dp, bottom = 12.dp)
            )
        }
    }
}

@Preview
@Composable
private fun DetailedAnimeItemCardPreview() {
    DenuAnimeTheme {
        Surface(color = MaterialTheme.colorScheme.surface) {
            DetailedAnimeItemCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                animeDetails = AnimeDetails(title = "gfweklkl jklf mklw mklwdcmklcdw mklwe")
            )
        }
    }
}