package com.gmail.denuelle42.denuanime.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@Composable
fun AnimeListItemCard(modifier: Modifier = Modifier, animeDetails: AnimeDetails) {
    OutlinedCard(
        onClick = {},
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(6.dp)
        ) {
            AsyncImage(
                model = animeDetails.images?.jpg?.large_image_url,
                placeholder = painterResource(R.drawable.baseline_image_24),
                contentDescription = stringResource(R.string.anime_banner),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.baseline_image_not_supported_24),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(width = 74.dp, height = 96.dp)
                    .clip(MaterialTheme.shapes.small)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = "Finished Airing | 2019, Winter",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = animeDetails.title.orEmpty()
                        .ifEmpty { stringResource(R.string.no_title_specified) },
                    style = MaterialTheme.typography.titleSmall)
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                tint = Color.LightGray
            )
        }
    }
}

@Preview
@Composable
private fun AnimeListItemCardPreview() {
    DenuAnimeTheme {
        AnimeListItemCard(
            animeDetails = AnimeDetails(

            )
        )
    }
}