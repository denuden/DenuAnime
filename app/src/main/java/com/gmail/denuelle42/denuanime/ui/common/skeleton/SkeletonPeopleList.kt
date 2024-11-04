package com.gmail.denuelle42.denuanime.ui.common.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun SkeletonPeopleList(modifier: Modifier = Modifier) {
    val state = rememberScrollState()
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = modifier.horizontalScroll(state = state)) {
        repeat(4){
            Box(
                modifier = Modifier.shimmer().size(80.dp).background(color = Color.LightGray, shape = CircleShape)
            )
        }
    }
}

@Preview
@Composable
private fun SkeletonPeopleListPreview() {
    SkeletonPeopleList()
}