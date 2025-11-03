package com.gmail.denuelle42.denuanime.ui.common.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
fun SkeletonCharacterListScreen(modifier: Modifier = Modifier) {
    Column {
        Row {
            Row(
                modifier = Modifier.weight(1f)
                    .padding(end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .shimmer()
                        .size(80.dp)
                        .background(color = Color.LightGray, shape = CircleShape)
                )
                Column {
                    Box(
                        modifier = Modifier
                            .shimmer()
                            .height(18.dp)
                            .fillMaxWidth()
                            .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
                    )
                    Box(
                        modifier = Modifier
                            .shimmer()
                            .padding(top = 12.dp)
                            .height(18.dp)
                            .fillMaxWidth()
                            .background(
                                color = Color.LightGray,
                                shape = MaterialTheme.shapes.small
                            )
                    )
                }
            }

            Row (
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    modifier = Modifier
                        .shimmer()
                        .size(80.dp)
                        .background(color = Color.LightGray, shape = CircleShape)
                )
                Column {
                    Box(
                        modifier = Modifier
                            .shimmer()
                            .height(18.dp)
                            .fillMaxWidth()
                            .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
                    )
                    Box(
                        modifier = Modifier
                            .shimmer()
                            .padding(top = 12.dp)
                            .height(18.dp)
                            .fillMaxWidth()
                            .background(
                                color = Color.LightGray,
                                shape = MaterialTheme.shapes.small
                            )
                    )
                }
            }
        }

        Row (modifier = Modifier.padding(top = 8.dp)){
            repeat(5){
                Box(
                    modifier = Modifier.shimmer()
                        .weight(1f)
                        .height(30.dp)
                        .padding(horizontal = 2.dp)
                        .background(
                            color = Color.LightGray,
                        )
                )
            }
        }
    }
}

    @Preview
    @Composable
    private fun SkeletonCharacterListScreenPreview() {
        DenuAnimeTheme {
            SkeletonCharacterListScreen(modifier = Modifier.fillMaxSize())
        }
    }