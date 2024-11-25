package com.gmail.denuelle42.denuanime.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


/**
 * https://www.youtube.com/watch?v=KFazs62lIkE
 *
 * Observe a Flow as events in a lifecycle-aware manner. This ensures:
 * - Events are only collected when the lifecycle is in the STARTED state.
 * - The collection runs on the main thread using the immediate dispatcher.
 *
 * It also allows passing optional keys (`key1` and `key2`) to trigger recomposition
 * when those keys change.
 *
 * USES
 * Observing global snackbar controller events:
 *
 * `
 * ObserveAsEvents(flow = SnackBarController.events, snackbarHostState) { event ->
 *     scope.launch {
 *         snackbarHostState.currentSnackbarData?.dismiss() // Dismiss ongoing snackbar
 *         val result = snackbarHostState.showSnackbar(
 *             message = event.message,
 *             actionLabel = event.action?.name,
 *             duration = SnackbarDuration.Long
 *         )
 *
 *         if (result == SnackbarResult.ActionPerformed) {
 *             event.action?.action?.invoke()
 *         }
 *     }
 * }
 * ```
 */
@Composable
fun <T>ObserveAsEvents(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent : (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle, key1 ,key2, flow) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
            withContext(Dispatchers.Main.immediate){
                flow.collect(onEvent)
            }
        }
    }
}



/**
 * https://stackoverflow.com/questions/71239101/how-to-listen-for-lifecycle-in-jetpack-compose
 * Makes an observer that observes lifecycle states and trigger events based on it
 * Automatically disposes that event and observer when lifecycle changes
 *
 * uses:
 *    ComposableLifecycle { source, event ->
 *         if (event == Lifecycle.Event.ON_RESUME) {
 *             viewModel.onEvent(HomeScreenEvents.GetAllArticles)
 *         }
 *     }
 */
@Composable
fun ComposableLifecycle(
    lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit
) {
    DisposableEffect(lifeCycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            onEvent(source, event)
        }
        lifeCycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }
}