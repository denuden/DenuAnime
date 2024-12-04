package com.gmail.denuelle42.denuanime.ui.anime.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SynopsisSection(modifier: Modifier = Modifier, synopsis: String?) {
    var isAboutContentExpanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .height(if (isAboutContentExpanded) Dp.Unspecified else 120.dp)
                .animateContentSize()
        ) {
            Text(
                text = synopsis?.takeIf { it.isNotEmpty() } ?: "-----",
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal
            )
        }

        TextButton(
            onClick =
            {
                isAboutContentExpanded = !isAboutContentExpanded
            },
            shape = MaterialTheme.shapes.medium,
            contentPadding = PaddingValues(vertical = 4.dp, horizontal = 2.dp),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(if(isAboutContentExpanded) "Hide" else "See more")
        }
    }
}