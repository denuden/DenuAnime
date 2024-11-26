package com.gmail.denuelle42.denuanime.ui.common

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableStateMapOf
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
import com.gmail.denuelle42.denuanime.data.remote.models.ImageType
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("ResourceAsColor")
@Composable
fun ImageSlider(
    modifier: Modifier = Modifier,
    images: List<ImageType>,
) {
    val state = rememberLazyListState()
    // Cache background colors for each image
    val colorCache = remember { mutableStateMapOf<String, Int>() }
    var backgroundColor by remember { mutableIntStateOf(R.color.black) }

    var selectedImage by remember { mutableIntStateOf(0) }

    val scope = rememberCoroutineScope()
    // Get the visible item and store it as the selected item
    LaunchedEffect(state) {
        snapshotFlow { state.firstVisibleItemIndex }.collect {  // use the new index
            selectedImage = it

            val image = images.getOrNull(it) //Get the image base on id
            //access the color based on image key, if key not the same then black
            backgroundColor = colorCache[image?.jpg?.image_url.orEmpty()] ?: R.color.black
        }
    }


    // Animate the background color
    val animatedColor by animateColorAsState(
        targetValue = Color(backgroundColor),
        label = "Background Color",
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing
        )
    )

    Box(
        modifier = Modifier
            .background(color = animatedColor)
    ){
        LazyRow(
            state = state,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
            modifier = modifier
        ) {
            items(images) { image ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image.jpg?.image_url.orEmpty())
                        .allowHardware(false)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.baseline_image_24),
                    contentDescription = stringResource(R.string.anime_banner),
                    contentScale = ContentScale.Fit,
                    error = painterResource(R.drawable.baseline_image_not_supported_24),
                    onSuccess = { result ->
                        val bitmap = result.result.image.toBitmap()

                        Palette.Builder(bitmap).generate { palette ->
                            val dominantColor =
                                palette?.getDominantColor(R.color.black) ?: R.color.black

                            colorCache[image.jpg?.image_url.orEmpty()] = dominantColor  //set color with image as key

                            // Update background color if this image is currently visible
                            if (images[selectedImage] == image) {
                                backgroundColor = dominantColor
                            }
                        }
                    },
                    modifier = Modifier.fillParentMaxSize()
                )
            }
        }

        IconButton(
            onClick = {
                scope.launch {
                    val index = if(selectedImage == 0) 0 else selectedImage - 1
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
                    val index = if(selectedImage == images.size - 1) images.size -1 else selectedImage + 1
                    state.animateScrollToItem(index = index)
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

@Preview
@Composable
private fun ImageSliderPreview() {
    DenuAnimeTheme {
        ImageSlider(
            images = listOf(ImageType()), modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
    }

}