package com.gmail.denuelle42.denuanime.ui.common

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.request.crossfade
import coil3.test.FakeImage
import coil3.toBitmap
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@SuppressLint("ResourceAsColor")
@Composable
fun AnimeItemCard(modifier: Modifier = Modifier, image: Any, title : String, height: Dp = 200.dp) {

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

    var expanded by remember { mutableStateOf(false) }
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = animatedColor,
        ),
        border = BorderStroke(2.5.dp, Color.Black),
        shape = RectangleShape,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
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

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(vertical = 12.dp, horizontal = 12.dp)
                    .clickable {
                        expanded = !expanded
                    }
            ){
                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown, contentDescription = stringResource(R.string.more_information),
                    tint = Color.White,
                    modifier = Modifier.padding(start = 6.dp).align(Alignment.Bottom)
                )
            }
        }
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .animateContentSize()
                .fillMaxWidth()
                .padding(if(expanded) 8.dp else 0.dp)
        ) {
            if(expanded) {
                Text("Sample Text", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview
@Composable
private fun AnimeItemCardPreview() {
    val previewHandler = AsyncImagePreviewHandler {
        FakeImage(color = R.color.purple_500)
    }


    DenuAnimeTheme {
        Surface(
            modifier = Modifier
                .background(Color.White)
                .padding(12.dp)
        ) {
            Column {
                CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
                    AnimeItemCard(
                        image = "https://cdn.myanimelist.net/images/anime/4/19644l.jpg",
                        title = "Boku no Hero Academia Season 2 Part 2 Boku no Hero Academia Season 2 Part 2 Boku no Hero Academia Season 2 Part 2"
                    )
                }
            }
        }
    }
}