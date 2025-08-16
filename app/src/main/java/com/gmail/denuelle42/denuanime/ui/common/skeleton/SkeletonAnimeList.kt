package com.gmail.denuelle42.denuanime.ui.common.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun SkeletonAnimeList(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .shimmer()
                .width(60.dp)
                .height(80.dp)
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
        )

        Column {
            Box(
                modifier = Modifier
                    .shimmer()
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .height(18.dp)
                    .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
            )
            Box(
                modifier = Modifier
                    .shimmer()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .padding(end = 30.dp)
                    .height(12.dp)
                    .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
            )
        }
    }
}

@Preview
@Composable
private fun SkeletonAnimeListPreview() {
    DenuAnimeTheme {
        Surface(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)
        ) {
            SkeletonAnimeList()
        }
    }
}