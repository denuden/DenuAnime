package com.gmail.denuelle42.denuanime.ui.common.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun SkeletonPeopleDetailsScreen(modifier: Modifier = Modifier) {
    val state = rememberScrollState()
    Column(modifier = modifier.verticalScroll(state)) {
        Box(
            modifier = Modifier
                .shimmer()
                .background(color = Color.Gray)
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = null,
                modifier = Modifier
                    .align(
                        Alignment.Center
                    )
                    .size(150.dp)
            )
        }

        Box(
            modifier = Modifier
                .shimmer()
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
                .fillMaxWidth()
                .height(26.dp)
        )


        Box(
            modifier = Modifier
                .shimmer()
                .padding(vertical = 2.dp, horizontal = 8.dp)
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
                .fillMaxWidth()
                .height(22.dp)
        )


        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .shimmer()
                .padding(vertical = 2.dp, horizontal = 8.dp)
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
                .width(200.dp)
                .height(16.dp)
        )
        Box(
            modifier = Modifier
                .shimmer()
                .padding(vertical = 2.dp, horizontal = 8.dp)
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
                .width(200.dp)
                .height(16.dp)
        )
        Box(
            modifier = Modifier
                .shimmer()
                .padding(vertical = 2.dp, horizontal = 8.dp)
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
                .width(200.dp)
                .height(16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .shimmer()
                .padding(vertical = 2.dp, horizontal = 8.dp)
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
                .fillMaxWidth()
                .height(100.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .shimmer()
                .padding(vertical = 2.dp, horizontal = 8.dp)
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
                .padding(end = 8.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = null,
                modifier = Modifier
                    .align(
                        Alignment.CenterStart
                    )
                    .size(100.dp)
            )

            Box(
                modifier = Modifier
                    .padding(start = 100.dp)
                    .background(color = Color.Black, shape = MaterialTheme.shapes.small)
                    .fillMaxWidth()
                    .height(16.dp)
                    .align(Alignment.CenterEnd)
            )
        }

        Box(
            modifier = Modifier
                .shimmer()
                .padding(vertical = 2.dp, horizontal = 8.dp)
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
                .padding(end = 8.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = null,
                modifier = Modifier
                    .align(
                        Alignment.CenterStart
                    )
                    .size(100.dp)
            )

            Box(
                modifier = Modifier
                    .padding(start = 100.dp)
                    .background(color = Color.Black, shape = MaterialTheme.shapes.small)
                    .fillMaxWidth()
                    .height(16.dp)
                    .align(Alignment.CenterEnd)
            )
        }
        Box(
            modifier = Modifier
                .shimmer()
                .padding(vertical = 2.dp, horizontal = 8.dp)
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
                .padding(end = 8.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = null,
                modifier = Modifier
                    .align(
                        Alignment.CenterStart
                    )
                    .size(100.dp)
            )

            Box(
                modifier = Modifier
                    .padding(start = 100.dp)
                    .background(color = Color.Black, shape = MaterialTheme.shapes.small)
                    .fillMaxWidth()
                    .height(16.dp)
                    .align(Alignment.CenterEnd)
            )
        }
        Box(
            modifier = Modifier
                .shimmer()
                .padding(vertical = 2.dp, horizontal = 8.dp)
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
                .padding(end = 8.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = null,
                modifier = Modifier
                    .align(
                        Alignment.CenterStart
                    )
                    .size(100.dp)
            )

            Box(
                modifier = Modifier
                    .padding(start = 100.dp)
                    .background(color = Color.Black, shape = MaterialTheme.shapes.small)
                    .fillMaxWidth()
                    .height(16.dp)
                    .align(Alignment.CenterEnd)
            )
        }

    }
}

@Preview
@Composable
private fun SkeletonPeopleDetailsScreenPreview() {
    DenuAnimeTheme {
        SkeletonPeopleDetailsScreen()
    }
}