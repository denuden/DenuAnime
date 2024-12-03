package com.gmail.denuelle42.denuanime.ui.anime.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.ui.common.RatingBarView
import com.gmail.denuelle42.denuanime.utils.orEmpty
import java.util.Locale


@Composable
fun AiredInfoSection(modifier: Modifier = Modifier, context: Context, animeDetails: AnimeDetails) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseSurface),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(
                text = "${animeDetails.season.orEmpty("--")} ${animeDetails.year ?: "--"} | ${
                    animeDetails.status.orEmpty(
                        "---"
                    )
                }".uppercase(Locale.getDefault()),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null)
                Text(
                    text = animeDetails.aired?.string.orEmpty("----"),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Light
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 6.dp),
                color = MaterialTheme.colorScheme.inverseOnSurface
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                ChipWithHeader(
                    title = "Rating/Score",
                    body = (animeDetails.score ?: 0.00).toString(),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
                ChipWithHeader(
                    title = "Scored by",
                    body = "%,d".format((animeDetails.scored_by ?: 0)),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
                ChipWithHeader(
                    title = "Rank",
                    body = (animeDetails.rank ?: 0.00).toString(),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
                ChipWithHeader(
                    title = "Popularity",
                    body = (animeDetails.popularity ?: 0.00).toString(),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                RatingBarView(
                    rating = animeDetails.score?.toFloat() ?: 0f,
                    scale = 1.3f,
                )
            }
        }
    }
}
