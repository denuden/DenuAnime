package com.gmail.denuelle42.denuanime.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@Composable
fun PeopleAvatarItem(modifier: Modifier = Modifier, image : Any, name : String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.baseline_account_circle_24),
            contentDescription = stringResource(R.string.voice_actor_actress),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.baseline_account_circle_24),
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .clip(CircleShape)
        )
        Text(text = name, style = MaterialTheme.typography.labelLarge, maxLines = 2, overflow = TextOverflow.Visible, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
    }
}

@Preview
@Composable
private fun PeopleAvatarItemPreview() {
    DenuAnimeTheme {
        Surface(modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)) {
            PeopleAvatarItem(
                image = "",
                name = "Hiroshi Kamiya"
            )
        }
    }
}