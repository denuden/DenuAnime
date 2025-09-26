package com.gmail.denuelle42.denuanime.ui.anime.characters.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.data.remote.models.animecharacters.VoiceActor
import com.gmail.denuelle42.denuanime.utils.AsyncImageAvatarWithErrorHandler
import com.gmail.denuelle42.denuanime.utils.clickableDelayed
import com.gmail.denuelle42.denuanime.utils.goURL

@Composable
fun PersonItem(
    modifier: Modifier = Modifier,
    data: VoiceActor,
) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        AsyncImageAvatarWithErrorHandler(
            model = data.person?.images?.jpg?.image_url,
            contentDescription = data.person?.name,
            shouldShowEnlargeButton = false,
            modifier = Modifier.size(70.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier.padding(start = 6.dp)
        ) {
            Text(
                text = if (data.person?.url.isNullOrEmpty()) "No external link" else "External site",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.clickableDelayed {
                    context.goURL(data.person?.url.orEmpty())
                }
            )
            Text(
                text = data.person?.name.orEmpty().ifEmpty { "----" },
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = data.language.orEmpty().ifEmpty { "----" },
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}