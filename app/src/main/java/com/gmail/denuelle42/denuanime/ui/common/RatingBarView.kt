package com.gmail.denuelle42.denuanime.ui.common

import android.widget.RatingBar
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun RatingBarView(modifier: Modifier = Modifier, rating : Float, scale : Float) {
    // Adds view to Compose
    AndroidView(
        modifier = modifier,
        factory = { context ->
            // Creates view
            RatingBar(context, null, android.R.attr.ratingBarStyleSmall).apply {
                numStars =10
                setIsIndicator(true)
                // Sets uteners for View -> Compose communication
                setOnClickListener {
                }
                stepSize = 0.1f
                scaleX = scale
                scaleY = scale
            }
        },
        update = { view ->
            // View's been inflated or state read in this block has been updated
            // Add logic here if necessary

            // As selectedItem is read here, AndroidView will recompose
            // whenever the state changes
            // Example of Compose -> View communication
            view.rating = rating
        }
    )
}

@Preview
@Composable
private fun RatingBarViewPreview() {
    RatingBarView(rating = .5f, modifier = Modifier.wrapContentWidth(), scale = 1.2f)
}