package com.gmail.denuelle42.denuanime.ui.common

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import coil3.request.allowHardware
import coil3.request.crossfade
import coil3.toBitmap
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("ResourceAsColor")
@Composable
fun ImageSlider(
    modifier: Modifier = Modifier,
    images: List<Any>,
) {
    val state = rememberLazyListState()
    var backgroundColor by remember { mutableIntStateOf(0) }
    var selectedImage by remember { mutableIntStateOf(0) }

    val scope = rememberCoroutineScope()
    // Get the visible item and store it as the selected item
    LaunchedEffect(state) {
        snapshotFlow { state.firstVisibleItemIndex }.collect {  // use the new index
            selectedImage = it
        }
    }

    // Animate the background color
    val animatedColor by animateColorAsState(
        targetValue = Color(backgroundColor),
        label = "Background Color"
    )
    LazyRow(
        state = state,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
        modifier = modifier
    ) {
        itemsIndexed(images) { index, image ->
            Box(
                modifier = Modifier
                    .background(color = animatedColor)
                    .fillParentMaxSize()
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
                    modifier = Modifier.matchParentSize()
                )

                IconButton(
                    onClick = {
                        scope.launch {
                            state.animateScrollToItem(index = selectedImage - 1)
                        }
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Gray.copy(
                            alpha = 0.2f
                        )
                    ),
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = {
                        scope.launch {
                            state.animateScrollToItem(index = selectedImage - 1)
                        }
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Gray.copy(
                            alpha = 0.2f
                        )
                    ),
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                // Indicator of image index
                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.align(Alignment.BottomCenter).padding(4.dp)
                ) {
                    repeat(images.size) { indicatorIndex ->
                        Icon(
                            imageVector = Icons.Default.Circle,
                            contentDescription = null,
                            tint = if (selectedImage == indicatorIndex) Color.Blue else Color.Gray,
                            modifier = Modifier.size(10.dp)
                        )
                    }

                }

            }
        }
    }
}

@Preview
@Composable
private fun ImageSliderPreview() {
    DenuAnimeTheme {
        ImageSlider(
            images = listOf("", ""), modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
    }

}