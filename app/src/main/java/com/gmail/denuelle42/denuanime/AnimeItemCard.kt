package com.gmail.denuelle42.denuanime

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import coil3.request.crossfade
import coil3.toBitmap
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@SuppressLint("ResourceAsColor")
@Composable
fun AnimeItemCard(modifier: Modifier = Modifier) {

    var backgroundColor by remember { mutableIntStateOf(0) }
    // Animate the background color
    val animatedColor by animateColorAsState(targetValue = Color(backgroundColor), label = "Background Color")

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = animatedColor,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://cdn.myanimelist.net/images/anime/4/19644l.jpg")
                .target(
                    onSuccess = { result ->
                        Palette.Builder(result.toBitmap()).generate { palette ->
                            // Consume the palette.
                            if (palette != null) {
                                backgroundColor =  palette.getDominantColor(R.color.black)
                            } else{
                                backgroundColor = R.color.black
                            }
                        }
                    },
                )
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.baseline_image_not_supported_24),
            placeholder = painterResource(R.drawable.baseline_image_24),
            contentDescription = stringResource(R.string.anime_banner),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(50.dp)
        )

        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .fillMaxSize()
        ) {
            Text(text = "gew")

        }
    }
}

@Preview
@Composable
private fun AnimeItemCardPreview() {
    DenuAnimeTheme {
        Surface(
            modifier = Modifier
                .background(Color.White)
                .padding(12.dp)
        ) {
            AnimeItemCard(modifier = Modifier
                .fillMaxWidth()
                .height(140.dp))
        }

    }
}