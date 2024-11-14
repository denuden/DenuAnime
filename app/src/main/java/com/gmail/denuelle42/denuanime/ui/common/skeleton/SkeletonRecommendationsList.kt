package com.gmail.denuelle42.denuanime.ui.common.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun SkeletonRecommendationsList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .shimmer()
                    .weight(1f, fill = false)
                    .background(color = Color.Gray, shape = CircleShape)
                    .size(160.dp)
            )

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Box(
                modifier = Modifier
                    .shimmer()
                    .weight(1f, fill = false)
                    .background(color = Color.Gray, shape = CircleShape)
                    .size(160.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .shimmer()
                    .weight(1f, fill = false)
                    .background(color = Color.Gray, shape = CircleShape)
                    .size(80.dp)
            )
            Box(
                modifier = Modifier
                    .shimmer()
                    .weight(3f, fill = false)
                    .background(color = Color.Gray, shape = CircleShape)
                    .size(220.dp)
                    .padding(horizontal = 6.dp)
            )
            Box(
                modifier = Modifier
                    .shimmer()
                    .weight(1f, fill = false)
                    .background(color = Color.Gray, shape = CircleShape)
                    .size(80.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .shimmer()
                    .weight(1f, fill = false)
                    .background(color = Color.Gray, shape = CircleShape)
                    .size(160.dp)
            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Box(
                modifier = Modifier
                    .shimmer()
                    .weight(1f, fill = false)
                    .background(color = Color.Gray, shape = CircleShape)
                    .size(160.dp)
            )
        }
    }
}

@Preview
@Composable
private fun SkeletonRecommendationsListPreview() {
    DenuAnimeTheme {
        SkeletonRecommendationsList()

    }

}