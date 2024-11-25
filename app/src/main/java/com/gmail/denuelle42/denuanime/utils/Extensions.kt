package com.gmail.denuelle42.denuanime.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.debugInspectorInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun String.debug(message: String) {
    Log.d(this, message)
}

fun EditText.modifyText(numberText: String) {
    this.setText(numberText)
    this.setSelection(numberText.length)
}

fun clearFocus(focusManager: FocusManager){
    focusManager.clearFocus()
}

/**
 * Modified modifier.clickable where 1second delay is imposed
 * to avoid spam clicks
 */
@Composable
fun Modifier.clickableDelayed(
    delayMillis: Long = 1000L,
    onClick: () -> Unit
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickableDelayed"
        properties["delayMillis"] = delayMillis
        properties["onClick"] = onClick
    }
) {
    var isClickable by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

   clickable(enabled = isClickable) {
        if (isClickable) {
            isClickable = false
            onClick()
            // Restore clickable state after the delay
            scope.launch {
                delay(delayMillis)
                isClickable = true
            }
        }
    }
}


fun Context.goURL(url: String) {
    try {
        val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(myIntent)
    } catch (e: ActivityNotFoundException) {
        this.toast("No application can handle this request. Please install a webbrowser")
        e.printStackTrace()
    }
}