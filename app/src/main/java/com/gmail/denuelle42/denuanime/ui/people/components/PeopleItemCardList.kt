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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.remote.models.people.People
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.AsyncImageWithErrorHandler
import com.gmail.denuelle42.denuanime.utils.clickableDelayed
import com.gmail.denuelle42.denuanime.utils.formatIsoDateAsLongDate

@Composable
fun PeopleItemCardList(modifier: Modifier = Modifier, people: People, onClickItem : () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier.clickableDelayed {
            onClickItem()
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()) {

            AsyncImageWithErrorHandler(
                model = people.images?.jpg?.image_url,
                shouldShowEnlargeButton = false,
                contentDescription = people.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )

            Column(modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)) {
                Text(
                    text = people.name ?: stringResource(R.string.error_unknown_name),
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.padding(vertical = 2.dp))
                Text(
                    text = "${people.given_name} ${people.family_name.orEmpty()}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal
                )
            }

            Column(modifier) {
                Text(
                    text = stringResource(R.string.label_birthday),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.align(Alignment.End)
                )
                Spacer(modifier = Modifier.padding(vertical = 2.dp))
                Text(
                    text =  formatIsoDateAsLongDate(people.birthday, customMessage = "-- -- --"),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Normal,
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
        PeopleItemCardList(people = People(
            name = "Tomokazu Seki",
            family_name = "関",
            given_name = "智一",
            birthday = "1975-01-28T00:00:00+00:00"
        )){}
    }
}