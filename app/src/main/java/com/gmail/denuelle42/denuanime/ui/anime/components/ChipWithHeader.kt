package com.gmail.denuelle42.denuanime.ui.anime.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@Composable
fun ChipWithHeader(modifier: Modifier = Modifier, title: String, body: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.secondaryContainer, shape = MaterialTheme.shapes.small)
            .padding(vertical = 8.dp, horizontal =2.dp)
            .width(IntrinsicSize.Min)

    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
                .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }

    }

}

@Preview
@Composable
private fun ChipWithHeaderPreview() {
    DenuAnimeTheme {
        ChipWithHeader(title = "Rating", body = "8.4")
    }
}