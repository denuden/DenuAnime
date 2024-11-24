package com.gmail.denuelle42.denuanime.ui.people.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.data.remote.models.people.Character
import com.gmail.denuelle42.denuanime.data.remote.models.people.Voices
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@Composable
fun AnimeVoicesItemCardList(modifier: Modifier = Modifier, voices: Voices) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()) {
            AsyncImage(
                model = voices.character?.images?.jpg?.image_url,
                contentDescription = voices.character?.name,
                placeholder = painterResource(R.drawable.baseline_image_24),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.baseline_image_not_supported_24),
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )

            Column(modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)) {
                Text(
                    text = voices.character?.name ?: stringResource(R.string.unknown_name),
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.padding(vertical = 2.dp))
                Text(
                    text = voices.anime?.title ?: stringResource(R.string.unknown_anime_title),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal
                )
            }

            Column(modifier) {
                Text(
                    text =  voices.role ?: "--- role",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PeopleItemCardListPreview() {
    DenuAnimeTheme {
        AnimeVoicesItemCardList(voices = Voices(
            character = Character(name = "Liu, Ryuushou"),
            anime = AnimeDetails(title = "Taisou Zamurai"),
            role = "Supporting"
        ))
    }
}