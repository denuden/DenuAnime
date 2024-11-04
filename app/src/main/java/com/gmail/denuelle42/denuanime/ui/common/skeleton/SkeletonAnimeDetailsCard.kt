package com.gmail.denuelle42.denuanime.ui.common.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkeletonAnimeDetailsCard(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
            .padding(12.dp)
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .shimmer()
                        .size(width = 80.dp, height = 25.dp)
                        .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
                )
            }
        }

        Icon(
            imageVector = Icons.Default.Image,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.shimmer().size(200.dp).align(Alignment.CenterHorizontally)
        )


        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .shimmer()
                    .padding(end = 50.dp, bottom = 20.dp)
                    .height(25.dp)
                    .fillMaxWidth()
                    .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .shimmer()
                            .height(12.dp)
                            .weight(1f)
                            .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .shimmer()
                            .height(12.dp)
                            .weight(1f)
                            .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
                    )
                }
            }
        }

    }
}

@Preview
@Composable
private fun PreviewSkeletonAnimeDetailsCard() {
    SkeletonAnimeDetailsCard()
}