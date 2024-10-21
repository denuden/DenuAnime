package com.gmail.denuelle42.denuanime.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.data.remote.models.people.People
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@Composable
fun PeopleList(modifier: Modifier = Modifier, items : List<People>) {
    val state = rememberLazyListState()
    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp), state = state, modifier = modifier) {
        items(items) { people ->
            PeopleAvatarItem(
                image = people.image,
                name = people.name,
                modifier = Modifier
                    .width(50.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PeopleListReview() {
    DenuAnimeTheme {
        Surface(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)
        ) {
            PeopleList(modifier = Modifier.fillMaxWidth(),


                items = listOf(
                    People(
                        image= "",
                        name = "Hiroshi Kamiya"
                    ),
                    People(
                        image= "",
                        name = "Ayane Sakura"
                    ),
                    People(
                        image= "",
                        name = "Inose Minari"
                    ),
                    People(
                        image= "",
                        name = "Kayano Ai"
                    ),
                    People(
                        image= "",
                        name = "Matsuoka Yoshitsugu"
                    ),
                    People(
                        image= "",
                        name = "Kenjiro Tsuda"
                    ),
                    People(
                        image= "",
                        name = "Kaji Yuki"
                    ),
                    People(
                        image= "",
                        name = "Aoi Koga"
                    ),
                    People(
                        image= "",
                        name = "Ayane Taketatsu"
                    ),
                    People(
                        image= "",
                        name = "Miku Ito"
                    ),
                )
            )
        }
    }
}