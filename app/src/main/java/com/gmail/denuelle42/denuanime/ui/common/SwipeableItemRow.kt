//package com.gmail.denuelle42.denuanime.ui.common
//
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.gestures.AnchoredDraggableState
//import androidx.compose.foundation.gestures.DraggableAnchors
//import androidx.compose.foundation.gestures.Orientation
//import androidx.compose.foundation.gestures.anchoredDraggable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.IntrinsicSize
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.RowScope
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.unit.IntOffset
//import androidx.compose.ui.unit.dp
//import kotlin.math.roundToInt
//
//// Define your anchors (states)
//enum class DragAnchors {
//    Start, // Content fully visible
//        End    // Content swiped to reveal action
//}
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun SwipeableItemRow(
//    onDelete: () -> Unit,
//    content: @Composable RowScope.() -> Unit
//) {
//
////    val density = LocalDensity.current
////    val swipeDistancePx = with(density) { 60.dp.toPx() } // How far to swipe to trigger action
////
////    val actionDragState = remember {
////        AnchoredDraggableState(
////            initialValue = SwipeAction.Neutral,
////            positionalThreshold = { distance: Float -> distance * 0.7f },
////            velocityThreshold = { with(density) { 80.dp.toPx() } },
////            anchors = DraggableAnchors {
////                SwipeAction.SwipedLeft at swipeDistancePx // Positive for right visual movement
////                SwipeAction.Neutral at 0f
////                SwipeAction.SwipedRight at -swipeDistancePx // Negative for left visual movement
////            },
////            snapAnimationSpec = spring(
////                dampingRatio = Spring.DampingRatioNoBouncy,
////                stiffness = Spring.StiffnessLow
////            ), // Animation for programmatic snapTo or very slow drags
////            decayAnimationSpec = exponentialDecay(), // Animation for flings
////            confirmValueChange = { true } // Allow all state changes by default
////        )
////    }
////
////    // Update anchors if swipeDistancePx changes (good practice)
////    LaunchedEffect(swipeDistancePx) {
////        actionDragState.updateAnchors(
////            DraggableAnchors {
////                SwipeAction.SwipedLeft at swipeDistancePx
////                SwipeAction.Neutral at 0f
////                SwipeAction.SwipedRight at -swipeDistancePx
////            }
////        )
////    }
////
////    // Observe changes in the drag state to update personIndex
////    // And snap back to neutral after an action
////    LaunchedEffect(actionDragState.currentValue) {
////        when (actionDragState.currentValue) {
////            SwipeAction.SwipedLeft -> {
////                if (data.voice_actors?.isNotEmpty() == true) {
////                    personIndex =
////                        (personIndex - 1 + data.voice_actors.size) % data.voice_actors.size
////                }
////                actionDragState.snapTo(SwipeAction.Neutral) // Snap back
////            }
////
////            SwipeAction.SwipedRight -> {
////                if (data.voice_actors?.isNotEmpty() == true) {
////                    personIndex = (personIndex + 1) % data.voice_actors.size
////                }
////                actionDragState.snapTo(SwipeAction.Neutral) // Snap back
////            }
////
////            SwipeAction.Neutral -> { /* Do nothing */
////            }
////        }
////    }
//
//
//
//    val density = LocalDensity.current
//    val revealWidthDp = 80.dp // How much to reveal for the delete button
//    val revealWidthPx = with(density) { revealWidthDp.toPx() }
//
//    @OptIn(ExperimentalFoundationApi::class)
//    val anchoredDraggableState = remember {
//        AnchoredDraggableState(
//            initialValue = DragAnchors.Start,
//            anchors = DraggableAnchors { // Anchors should be defined here now
//                DragAnchors.Start at 0f
//                DragAnchors.End at -revealWidthPx
//            },
//            positionalThreshold = { totalDistance: Float -> totalDistance * 0.5f },
//            velocityThreshold = { with(density) { 100.dp.toPx() } },
//
//            // New / More Explicit Parameters:
//            snapAnimationSpec = androidx.compose.animation.core.spring(), // Animation for snapping without fling
//            decayAnimationSpec = androidx.compose.animation.core.exponentialDecay(), // Animation for flinging
//            confirmValueChange = {
//                // This lambda allows you to conditionally block a swipe to a new state.
//                // Return true to allow the change, false to prevent it.
//                // For most simple cases, always allowing it is fine.
//                true
//            }
//        )
//    }
//
//    // Update anchors if revealWidthPx changes (e.g., due to density changes, though unlikely here)
//    // This is more important if your anchors depend on dynamic layout sizes.
//    LaunchedEffect(revealWidthPx) {
//        anchoredDraggableState.updateAnchors(
//            DraggableAnchors {
//                DragAnchors.Start at 0f
//                DragAnchors.End at -revealWidthPx
//            }
//        )
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(IntrinsicSize.Min) // Make Box height wrap its content
//            .background(MaterialTheme.colorScheme.errorContainer) // Background for the revealed area
//    ) {
//        // Action content (e.g., Delete button) - Aligned to the end
//        IconButton(
//            onClick = onDelete,
//            modifier = Modifier
//                .align(Alignment.CenterEnd)
//                .width(revealWidthDp) // Occupy the reveal width
//                .fillMaxHeight()      // Fill the height of the row
//        ) {
//            Icon(
//                imageVector = Icons.Filled.Delete,
//                contentDescription = "Delete",
//                tint = MaterialTheme.colorScheme.onErrorContainer
//            )
//        }
//
//        // Your main content Row that will be draggable
//        Row(
//            modifier = Modifier
//                .offset {
//                    // Apply the horizontal offset based on the draggable state
//                    IntOffset(
//                        x = anchoredDraggableState
//                            .requireOffset() // Use requireOffset for non-nullable offset
//                            .roundToInt(),
//                        y = 0
//                    )
//                }
//                .anchoredDraggable(
//                    state = anchoredDraggableState,
//                    orientation = Orientation.Horizontal,
//                    reverseDirection = false
//                )
//                .background(MaterialTheme.colorScheme.surfaceVariant) // Background for the item content
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Pass the composable content lambda to this RowScope
//            content()
//        }
//    }
//}