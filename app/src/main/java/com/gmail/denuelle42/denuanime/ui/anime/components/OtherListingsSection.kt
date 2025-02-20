package com.gmail.denuelle42.denuanime.ui.anime.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.clickableDelayed
import com.gmail.denuelle42.denuanime.utils.goURL
import com.gmail.denuelle42.denuanime.utils.orEmpty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherListingsSection(modifier: Modifier = Modifier, animeDetails: AnimeDetails) {
    var state by remember { mutableIntStateOf(0) }
    val titles = listOf(
        stringResource(R.string.label_studio),
        stringResource(R.string.label_producers),
        stringResource(R.string.label_licensor),
        stringResource(R.string.label_opening_themes),
        stringResource(R.string.label_ending_themes),
        stringResource(R.string.label_external),
        stringResource(R.string.label_streaming)
    )
    val context = LocalContext.current
    Column(modifier = modifier) {
        PrimaryScrollableTabRow(selectedTabIndex = state) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = state == index,
                    onClick = { state = index },
                    text = { Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis) }
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        when (state) {
            0 -> {
                if (animeDetails.studios.isNullOrEmpty()) {
                    Text(
                        text = stringResource(R.string.error_no_studios_found),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
                    )
                } else {
                    animeDetails.studios.forEach {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 12.dp)
                                .clip(MaterialTheme.shapes.small)
                                .clickableDelayed {
                                    context.goURL(it.url.orEmpty())
                                }
                                .padding(horizontal = 6.dp)
                        ) {
                            Text(
                                text = it.name.orEmpty("-----"),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,contentDescription = null)
                        }
                    }
                }
            }

            1 -> {
                if (animeDetails.producers.isNullOrEmpty()) {
                    Text(
                        text = stringResource(R.string.error_no_producers_found),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
                    )
                } else {
                    animeDetails.producers.forEach {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 12.dp)
                                .clip(MaterialTheme.shapes.small)
                                .clickableDelayed {
                                    context.goURL(it.url.orEmpty())
                                }
                                .padding(horizontal = 6.dp)
                        ) {
                            Text(
                                text = it.name.orEmpty("-----"),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,contentDescription = null)
                        }
                    }
                }
            }

            2 -> {
                if (animeDetails.licensors.isNullOrEmpty()) {
                    Text(
                        text = stringResource(R.string.error_no_licensors_found),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
                    )
                } else {
                    animeDetails.licensors.forEach {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 12.dp)
                                .clip(MaterialTheme.shapes.small)
                                .clickableDelayed {
                                    context.goURL(it.url.orEmpty())
                                }
                                .padding(horizontal = 6.dp)
                        ) {
                            Text(
                                text = it.name.orEmpty("-----"),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,contentDescription = null)
                        }
                    }
                }
            }

            3 -> {
                if (animeDetails.theme?.openings.isNullOrEmpty()) {
                    Text(
                        text = stringResource(R.string.error_no_openings_found),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
                    )
                } else {
                    animeDetails.theme?.openings?.onEach {
                        Text(
                            text = it.orEmpty("-----"),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
                        )
                    }
                }
            }

            4 -> {
                if (animeDetails.theme?.endings.isNullOrEmpty()) {
                    Text(
                        text = stringResource(R.string.error_no_endings_found),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
                    )
                } else {
                    animeDetails.theme?.endings?.onEach {
                        Text(
                            text = it.orEmpty("-----"),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
                        )
                    }
                }
            }

            5 -> {
                if (animeDetails.external.isNullOrEmpty()) {
                    Text(
                        text = "No External Found",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
                    )
                } else {
                    animeDetails.external.forEach {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 12.dp)
                                .clip(MaterialTheme.shapes.small)
                                .clickableDelayed {
                                    context.goURL(it.url.orEmpty())
                                }
                                .padding(horizontal = 6.dp)
                        ) {
                            Text(
                                text = it.name.orEmpty("-----"),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,contentDescription = null)
                        }
                    }
                }
            }

            6 -> {
                if (animeDetails.streaming.isNullOrEmpty()) {
                    Text(
                        text = stringResource(R.string.error_no_streaming_found),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
                    )
                } else {
                    animeDetails.streaming.forEach {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 12.dp)
                                .clip(MaterialTheme.shapes.small)
                                .clickableDelayed {
                                    context.goURL(it.url.orEmpty())
                                }
                                .padding(horizontal = 6.dp)
                        ) {
                            Text(
                                text = it.name.orEmpty("-----"),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,contentDescription = null)
                        }
                    }
                }
            }
        }
    }

}

@Preview
@Composable
private fun OtherListingsSectionPreview() {
    DenuAnimeTheme {
        Surface(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)
        ) {
            OtherListingsSection(animeDetails = AnimeDetails())
        }
    }
}