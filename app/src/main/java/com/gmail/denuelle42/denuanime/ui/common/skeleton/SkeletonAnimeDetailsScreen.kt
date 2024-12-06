package com.gmail.denuelle42.denuanime.ui.common.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun SkeletonAnimeDetailsScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(modifier = modifier.verticalScroll(scrollState)) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .shimmer()
                .fillMaxWidth()
                .height(200.dp)
                .background(color = Color.LightGray)
        ) {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .shimmer()
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
                .height(22.dp)
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .shimmer()
                .padding(horizontal = 8.dp, vertical = 2.dp)
                .fillMaxWidth()
                .height(18.dp)
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
        )
        Box(
            modifier = Modifier
                .shimmer()
                .padding(horizontal = 8.dp, vertical = 2.dp)
                .fillMaxWidth()
                .height(18.dp)
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
        )

        Spacer(modifier = Modifier.height(12.dp))
        SkeletonGenreList(modifier = Modifier.padding(horizontal = 8.dp))

        Box(
            modifier = Modifier
                .shimmer()
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
                .fillMaxWidth()
                .height(100.dp)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .shimmer()
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                    .fillMaxWidth()
                    .height(18.dp)
                    .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
            )
            Box(
                modifier = Modifier
                    .shimmer()
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                    .fillMaxWidth()
                    .height(18.dp)
                    .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
            )

            HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
            Row() {
                Box(
                    modifier = Modifier
                        .shimmer()
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                        .height(32.dp)
                        .weight(1f)
                        .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
                )
                Box(
                    modifier = Modifier
                        .shimmer()
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                        .height(32.dp)
                        .weight(1f)
                        .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
                )
                Box(
                    modifier = Modifier
                        .shimmer()
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                        .height(32.dp)
                        .weight(1f)
                        .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
                )
                Box(
                    modifier = Modifier
                        .shimmer()
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                        .height(32.dp)
                        .weight(1f)
                        .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
                )
            }
            Box(
                modifier = Modifier
                    .shimmer()
                    .padding(horizontal = 8.dp, vertical = 3.dp)
                    .fillMaxWidth()
                    .height(18.dp)
                    .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
            )
        }


        Row(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
                .height(IntrinsicSize.Min)
                .padding(4.dp)
        ) {

            Box(
                modifier = Modifier
                    .shimmer()
                    .padding(horizontal = 2.dp, vertical = 2.dp)
                    .fillMaxHeight()
                    .weight(1f)
                    .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
            )


            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .shimmer()
                        .padding(horizontal = 3.dp, vertical = 2.dp)
                        .weight(1f)
                        .heightIn(min = 82.dp)
                        .fillMaxWidth()
                        .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
                )
                Box(
                    modifier = Modifier
                        .shimmer()
                        .padding(horizontal = 3.dp, vertical = 2.dp)
                        .weight(1f)
                        .heightIn(min = 82.dp)
                        .fillMaxWidth()
                        .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
                )
            }
        }

    }
}

@Preview
@Composable
private fun SkeletonAnimeDetailsScreenPreview() {
    DenuAnimeTheme {
        Surface(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)
        ) {
            SkeletonAnimeDetailsScreen()
        }
    }
}