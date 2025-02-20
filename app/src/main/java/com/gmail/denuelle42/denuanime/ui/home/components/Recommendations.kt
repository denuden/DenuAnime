package com.gmail.denuelle42.denuanime.ui.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.AsyncImageWithErrorHandler
import com.gmail.denuelle42.denuanime.utils.clickableDelayed
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Recommendations(
    modifier: Modifier = Modifier,
    isPrevButtonEnabled: Boolean = false,
    isNextButtonEnabled: Boolean = false,
    list: List<AnimeDetails>, //fix size of 7
    delayMillis: Long = 900L, // 1 second delay
    onClickPrevButton: () -> Unit,
    onClickNextButton: () -> Unit,
    onClickImage: (id : Int) -> Unit,
) {
    //to make sure that button wont be spammed on next and prev button
    var canClick by remember { mutableStateOf(true) }
    LaunchedEffect(canClick) {
        if (!canClick) {
            delay(delayMillis)
            canClick = true
        }
    }

    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        ) {
            TextButton(
                onClick = {
                    onClickPrevButton()
                    canClick = false
                },
                enabled = isPrevButtonEnabled && canClick,
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, stringResource(R.string.btn_prev))
                Text(stringResource(R.string.btn_prev))
            }

            Text(
                text = stringResource(R.string.label_recommendations).uppercase(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(2f)
            )

            TextButton(
                onClick = {
                    onClickNextButton()
                    canClick = false
                },
                enabled = isNextButtonEnabled && canClick,
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, stringResource(R.string.btn_next))
                Text(stringResource(R.string.btn_next))
            }
        }
        if (list.isNotEmpty()) {
            RecommendationsContent(
                list = list,
                onClickImage = onClickImage
            )
        }
    }
}


@Composable
fun RecommendationsContent(
    modifier: Modifier = Modifier,
    list : List<AnimeDetails>, //Fix size of 7
    onClickImage : (id : Int) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            list.getOrNull(0)?.let {
                RecommendationsImage(
                    image = it.images?.jpg?.image_url.orEmpty(),
                    title = it.title.orEmpty(),
                    imageSize = 160.dp,
                    modifier = Modifier.weight(1f, fill = false)
                        .clickableDelayed {
                            it.mal_id?.let { id -> onClickImage(id) }
                        }
                )
            }
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            list.getOrNull(1)?.let {
                RecommendationsImage(
                    image = it.images?.jpg?.image_url.orEmpty(),
                    title = it.title.orEmpty(),
                    imageSize = 160.dp,
                    modifier = Modifier.weight(1f, fill = false)
                        .clickableDelayed {
                            it.mal_id?.let { id -> onClickImage(id) }
                        }
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            list.getOrNull(2)?.let {
                RecommendationsImage(
                    image = it.images?.jpg?.image_url.orEmpty(),
                    title = it.title.orEmpty(),
                    imageSize = 80.dp,
                    modifier = Modifier.weight(1f, fill = false)
                        .clickableDelayed {
                            it.mal_id?.let { id -> onClickImage(id) }
                        }
                )
            }
            list.getOrNull(3)?.let {
                RecommendationsImage(
                    image = it.images?.jpg?.image_url.orEmpty(),
                    title = it.title.orEmpty(),
                    imageSize = 220.dp,
                    modifier = Modifier
                        .weight(3f, fill = false)
                        .padding(horizontal = 6.dp)
                        .clickableDelayed {
                            it.mal_id?.let { id -> onClickImage(id) }
                        }
                )
            }
            list.getOrNull(4)?.let {
                RecommendationsImage(
                    image = it.images?.jpg?.image_url.orEmpty(),
                    title = it.title.orEmpty(),
                    imageSize = 80.dp,
                    modifier = Modifier.weight(1f, fill = false)
                        .clickableDelayed {
                            it.mal_id?.let { id -> onClickImage(id) }
                        }
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            list.getOrNull(5)?.let {
                RecommendationsImage(
                    image = it.images?.jpg?.image_url.orEmpty(),
                    title = it.title.orEmpty(),
                    imageSize = 160.dp,
                    modifier = Modifier.weight(1f, fill = false)
                        .clickableDelayed {
                            it.mal_id?.let { id -> onClickImage(id) }
                        }
                )
            }
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            list.getOrNull(6)?.let {
                RecommendationsImage(
                    image = it.images?.jpg?.image_url.orEmpty(),
                    title = it.title.orEmpty(),
                    imageSize = 160.dp,
                    modifier = Modifier.weight(1f, fill = false)
                        .clickableDelayed {
                            it.mal_id?.let { id -> onClickImage(id) }
                        }
                )
            }
        }
    }
}

@Composable
fun RecommendationsImage(modifier: Modifier = Modifier, image: Any, imageSize: Dp, title: String) {
    val gradientColors: List<Color> by remember {
        mutableStateOf(
            listOf(
                Color.Transparent,
                Color(0xFF000000)
            )
        )
    }
    var imageHolder by remember { mutableStateOf(image) }
    var isImageVisible by remember { mutableStateOf(true) }

    LaunchedEffect(image) {
        // Hide the image, trigger exit animation
        isImageVisible = false

        // Wait for the exit animation to finish before updating image
        delay(300) // Adjust this to match exit animation duration
        imageHolder = image

        // Show the new image, triggering enter animation
        isImageVisible = true
    }

    AnimatedVisibility(
        visible = isImageVisible,
        enter = scaleIn(animationSpec = tween(durationMillis = 650)),
        exit = scaleOut(animationSpec = tween(durationMillis = 300))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.shadow(
                elevation = 4.dp,
                shape = CircleShape,
            ),
        ) {
            AsyncImageWithErrorHandler(
                model = imageHolder,
                shouldShowEnlargeButton = false,
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
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(vertical = 16.dp, horizontal = 26.dp),
                )

            }
        }
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
                color = if (selected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.extraSmall
            )
            .clickable { onClick() }
            .padding(vertical = 5.dp)

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
            Recommendations(
                modifier = Modifier,
                onClickNextButton = {},
                onClickPrevButton = {},
                list = listOf(

                ),
                onClickImage = {}
            )
        }

    }
}