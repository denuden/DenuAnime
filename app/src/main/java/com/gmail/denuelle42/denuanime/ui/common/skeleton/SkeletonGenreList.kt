package com.gmail.denuelle42.denuanime.ui.common.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun SkeletonGenreList(modifier: Modifier = Modifier) {
    val state = rememberScrollState()
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.horizontalScroll(state = state)
    ) {
        repeat(5) {
            Box(
                modifier = modifier
                    .shimmer()
                    .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
                    .size(height = 32.dp, width = 60.dp)
            )
        }
    }
}

@Preview
@Composable
private fun SkeletonGenreListPreview() {
    SkeletonGenreList()
}