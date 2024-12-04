package com.gmail.denuelle42.denuanime.ui.anime.components

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil3.compose.AsyncImage
import coil3.toBitmap
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@SuppressLint("ResourceAsColor")
@Composable
fun AnimeHeader(
    modifier: Modifier = Modifier,
    image: Any,
    title: String,
    titleJp: String,
    titleEn: String,
) {
    var backgroundColor by remember { mutableIntStateOf(0) }
    // Animate the background color
    val animatedColor by animateColorAsState(
        targetValue = Color(backgroundColor),
        label = "Background Color"
    )

    Column(modifier = modifier) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color = animatedColor)
                .fillMaxWidth()
                .height(250.dp),
        ) {
            AsyncImage(
                model = image,
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
        }
        Spacer(modifier = Modifier.height(6.dp))
        Column(modifier = Modifier.padding(8.dp)) {

            Text(
                text = title, style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )


            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = titleJp,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Light,
            )
            Text(
                text = titleEn,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Light,
            )
        }
    }

}

@Preview
@Composable
private fun AnimeImageBannerPreview() {
    DenuAnimeTheme {
        AnimeHeader(
            image = "h",
            modifier = Modifier,
            title = "wqearstdtfjgzsreestrre ",
            titleJp = "dsvs e ea gae ae ew awe",
            titleEn = "dgerver e er re er",
        )
    }

}