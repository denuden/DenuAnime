package com.gmail.denuelle42.denuanime.ui.common

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun SimpleSwipeableRow(
    modifier: Modifier = Modifier,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    var horizontalDragOffset by remember { mutableFloatStateOf(0f) }
    val density = LocalDensity.current
    // Define a minimum swipe distance in dp, then convert to pixels
    // Adjust this value based on how sensitive you want the swipe detection to be
    val swipeThresholdDp = 50.dp
    val swipeThresholdPx = with(density) { swipeThresholdDp.toPx() }

    Row(
        modifier = modifier
            .pointerInput(Unit) { // Pass Unit if the key doesn't change, or any relevant key
                detectHorizontalDragGestures(
                    onDragStart = {
                        // Reset drag offset at the start of a new gesture
                        horizontalDragOffset = 0f
                    },
                    onDragEnd = {
                        // Check if the total drag displacement exceeds the threshold
                        when {
                            horizontalDragOffset > swipeThresholdPx -> {
                                // Positive offset means dragged from left to right (swiped right)
                                onSwipeRight()
                            }
                            horizontalDragOffset < -swipeThresholdPx -> {
                                // Negative offset means dragged from right to left (swiped left)
                                onSwipeLeft()
                            }
                        }
                        // Optionally, you might want to reset horizontalDragOffset here too
                        // or animate the row back to its original position if you were offsetting it.
                        // For pure gesture detection without visual feedback, this is fine.
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        change.consume() // Consume the drag event to prevent propagation if needed
                        horizontalDragOffset += dragAmount // Accumulate the drag amount
                        // If you wanted visual feedback during the drag (like moving the Row),
                        // you would use this horizontalDragOffset to update an offset modifier.
                        // For pure detection, just accumulating is enough.
                    }
                )
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}