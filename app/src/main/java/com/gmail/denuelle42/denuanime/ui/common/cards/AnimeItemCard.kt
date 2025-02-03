package com.gmail.denuelle42.denuanime.ui.common.cards

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.test.FakeImage
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.AsyncImageWithBackgroundPalette

@SuppressLint("ResourceAsColor")
@Composable
fun AnimeItemCard(modifier: Modifier = Modifier, image: Any, title : String) {

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
            AsyncImageWithBackgroundPalette(
                model = image,
                shouldShowEnlargeButton = false,
                onPaletteBuilderSuccess = {
                    backgroundColor = it
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