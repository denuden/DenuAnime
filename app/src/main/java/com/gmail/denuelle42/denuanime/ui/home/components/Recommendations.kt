package com.gmail.denuelle42.denuanime.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Recommendations(modifier: Modifier = Modifier) {
    var state by remember { mutableIntStateOf(0) }
    val titles = listOf("Anime", "Manga")
    Column(modifier = modifier) {
        SecondaryTabRow(
            selectedTabIndex = state,
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            indicator = {},
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .padding(horizontal = 8.dp)
        ) {
            titles.forEachIndexed { index, title ->
                CustomRecommendationsTab(
                    title = title,
                    onClick = { state = index },
                    selected = (index == state),
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 6.dp))
        RecommendationsContent()
    }
}


@Composable
fun RecommendationsContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(horizontal = 34.dp)) {
            RecommendationsImage(
                image = "",
                imageSize = 140.dp,
                modifier = Modifier.weight(1f, fill = false)
            )
            Spacer(modifier = Modifier.padding(horizontal = 6.dp))
            RecommendationsImage(
                image = "",
                imageSize = 140.dp,
                modifier = Modifier.weight(1f, fill = false)
            )
        }

        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            RecommendationsImage(
                image = "",
                imageSize = 80.dp,
                modifier = Modifier.weight(1f, fill = false)
            )
            RecommendationsImage(
                image = "",
                imageSize = 180.dp,
                modifier = Modifier.weight(2f, fill = false).padding(horizontal = 6.dp)
            )
            RecommendationsImage(
                image = "",
                imageSize = 80.dp,
                modifier = Modifier.weight(1f, fill = false)
            )
        }

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(horizontal = 34.dp)) {
            RecommendationsImage(
                image = "",
                imageSize = 140.dp,
                modifier = Modifier.weight(1f, fill = false)
            )
            Spacer(modifier = Modifier.padding(horizontal = 6.dp))
            RecommendationsImage(
                image = "",
                imageSize = 140.dp,
                modifier = Modifier.weight(1f, fill = false)
            )
        }
    }
}

@Composable
fun RecommendationsImage(modifier: Modifier = Modifier, image: Any, imageSize: Dp) {
    val gradientColors: List<Color> = listOf(
        Color.Transparent,
        Color(0xFF000000)
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        AsyncImage(
            model = image,
            placeholder = painterResource(R.drawable.baseline_image_24),
            contentDescription = stringResource(R.string.anime_banner),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.baseline_image_not_supported_24),
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape)
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(CircleShape)
                .background(
                    brush = Brush.verticalGradient(
                        colors = gradientColors,
                    )
                )
        )
    }
}

@Composable
fun CustomRecommendationsTab(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    title: String
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .background(
                color = if (selected) Color.White else MaterialTheme.colorScheme.surfaceContainerHighest,
                shape = MaterialTheme.shapes.extraSmall
            )
            .padding(vertical = 5.dp)
            .clickable { onClick() }
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview()
@Composable
private fun RecommendationsPreview() {
    DenuAnimeTheme {
        Surface(
            color = Color.White
        ) {
            Recommendations()
        }

    }
}