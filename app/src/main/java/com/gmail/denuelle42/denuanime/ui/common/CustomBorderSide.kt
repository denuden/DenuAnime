package com.gmail.denuelle42.denuanime.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.ui.home.BorderSide

@Composable
fun CustomBorderSide(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.drawBehind {
            val strokeWidth = (1.dp).toPx()
            val yOffset = size.height - strokeWidth / 2
            val borderColor = Color.LightGray
            val  borderSide: BorderSide = BorderSide.Right

            when (borderSide) {
                BorderSide.Top -> drawLine(
                    color = borderColor,
                    start = Offset(0f, strokeWidth / 2),
                    end = Offset(size.width, strokeWidth / 2),
                    strokeWidth = strokeWidth
                )
                BorderSide.Bottom -> drawLine(
                    color = borderColor,
                    start = Offset(0f, yOffset),
                    end = Offset(size.width, yOffset),
                    strokeWidth = strokeWidth
                )
                BorderSide.Left -> drawLine(
                    color = borderColor,
                    start = Offset(strokeWidth / 2, 0f),
                    end = Offset(strokeWidth / 2, size.height),
                    strokeWidth = strokeWidth
                )
                BorderSide.Right -> drawLine(
                    color = borderColor,
                    start = Offset(size.width - strokeWidth / 2, size.height / 4),
                    end = Offset(size.width - strokeWidth / 2, size.height),
                    strokeWidth = strokeWidth
                )
            }
        }
    )
}