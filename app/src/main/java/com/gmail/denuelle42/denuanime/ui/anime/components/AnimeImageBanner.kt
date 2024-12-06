package com.gmail.denuelle42.denuanime.ui.anime.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.AsyncImageWithErrorHandler

@SuppressLint("ResourceAsColor")
@Composable
fun AnimeHeader(
    modifier: Modifier = Modifier,
    image: Any,
    title: String,
    titleJp: String,
    titleEn: String,
    onEnlargeImage: () -> Unit
) {

    Column(modifier = modifier) {
        AsyncImageWithErrorHandler(
            model = image,
            onEnlargeImage = onEnlargeImage,
            modifier = Modifier.fillMaxWidth()
                .height(300.dp),
        )

        Spacer(modifier = Modifier.height(6.dp))
        Column(modifier = Modifier.padding(8.dp)) {

            Text(
                text = title, style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )


            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = titleJp,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Light,
            )
            Text(
                text = titleEn,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Light,
            )
        }
    }

}

@Preview
@Composable
private fun AnimeImageBannerPreview() {
    DenuAnimeTheme {
        AnimeHeader(
            image = "h",
            modifier = Modifier,
            title = "wqearstdtfjgzsreestrre ",
            titleJp = "dsvs e ea gae ae ew awe",
            titleEn = "dgerver e er re er",
        ){}
    }

}