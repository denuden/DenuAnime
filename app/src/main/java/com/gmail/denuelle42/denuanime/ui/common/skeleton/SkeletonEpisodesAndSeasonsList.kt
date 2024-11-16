package com.gmail.denuelle42.denuanime.ui.common.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun SkeletonEpisodesAndSeasonsList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(bottom = 6.dp)
            .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .shimmer()
                    .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
                    .size(width = 74.dp, height = 96.dp)
            )

            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(start = 8.dp)) {
                Box(
                    modifier = Modifier
                        .shimmer()
                        .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
                        .fillMaxWidth()
                        .height(22.dp)
                )
                Spacer(modifier = Modifier.padding(vertical = 6.dp))
                Box(
                    modifier = Modifier
                        .shimmer()
                        .padding(end = 36.dp)
                        .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
                        .fillMaxWidth()
                        .height(15.dp)
                )
            }
        }

    }
}

@Preview
@Composable
private fun SkeletonEpisodesAndSeasonsListPreview() {
    SkeletonEpisodesAndSeasonsList(modifier = Modifier.fillMaxWidth())
}