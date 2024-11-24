package com.gmail.denuelle42.denuanime.ui.people

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.data.remote.models.people.Character
import com.gmail.denuelle42.denuanime.data.remote.models.people.Voices
import com.gmail.denuelle42.denuanime.navigation.NavigationScreens
import com.gmail.denuelle42.denuanime.ui.common.ImageSlider
import com.gmail.denuelle42.denuanime.ui.people.components.AnimeVoicesItemCardList
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailsScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (NavigationScreens) -> Unit,
) {

    PersonDetailsScreenContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailsScreenContent(modifier: Modifier = Modifier) {
    val lazyListState = rememberLazyListState()
    var isAboutContentExpanded by remember { mutableStateOf(false) }
    var tabState by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Voices")

    LazyColumn(state = lazyListState) {

        //Image
        item {
            ImageSlider(
                images = listOf("", "", "", ""),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }

        //Text Details
        item {
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Row {
                    Text(
                        "Hiroshi Kamiya",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "(神谷 浩史)", modifier = Modifier.padding(start = 6.dp),
                        style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Normal
                    )
                }

                Text(
                    "Alternate Names: ヒロC, HiroC, Kamiyan",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Light
                )

                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = "Birthday"
                    )
                    Text(
                        text = "September 5, 2001",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Birthday"
                    )
                    Text(
                        text = "10,020",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Link,
                        contentDescription = "Birthday",
                        tint = Color.Blue
                    )
                    Text(
                        text = "https://myanimelist.net/people/118/Hiroshi_Kamiya",
                        color = Color.Blue,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .height(if (isAboutContentExpanded) Dp.Unspecified else 120.dp)
                        .animateContentSize()
                ) {
                    Text(
                        "Birth place: Matsudo, Chiba Prefecture, Japan\nHeight: 167 cm (5'6\")\nWeight: 53 kg (117 lbs)\nBlood type: A\n\nKamiya Hiroshi went to Aoni Juku and decided to pursue voice acting. Since then, he's been affiliated with Aoni Production from his debut in 1994 to present.\n\nKamiya Hiroshi hosts several radio programs, one of the oldest and most prevalent programs he hosts together with a fellow seiyuu Ono Daisuke is Kamiya Hiroshi Ono Daisuke no DearGirl: Stories (神谷浩史・小野大輔のDearGirl～Stories～) since April 2007. The program they hosted together won \"Best Personality Awards\" in the 9th Annual Seiyuu Awards in 2015.\n\nHe and Ono Daisuke are vocalists of MasochistiC Ono BanD (MOB) that debuted in Nippon Budoukan in 2013 through the DearGirl: Stories Festival Carnival Matsuri. MOB went on hiatus in 2015 but the band announced their coming back on the 10th-anniversary celebration of his and Ono Daisuke's radio program, that was held on July 25, 2016.\n\nIn May 2010, he and Miyu Irino banded a Kiramune-unit called KAmiYU, for Mokei Senshi Gunpla Builders Beginning G theme song. First Mini-album \"link-up\" was released on August 3, 2011.\n\nAwards:\n- 2nd Seiyuu Awards (2008) - Best Supporting Actor Award\n- 3rd Seiyuu Awards (2009) - Best Actor and Best Personality Awards\n- Tokyo Anime Awards (2010) - Voice Actor Award\n- 6th Seiyuu Awards (2012) - Most Votes Award\n- 7th Seiyuu Awards (2013) - Most Votes Award\n- 8th Seiyuu Awards (2014) - Most Votes Award\n- 9th Seiyuu Awards (2015) - Best Personality and Most Votes Awards\n- 10th Seiyuu Awards (2016) - Most Votes Award - winning the award for 5 consecutive times earned him induction into Seiyuu Awards' Hall of Fame\n- 13th Seiyuu Awards (2019) - Most Votes Award\n- 14th Seiyuu Awards (2020) - Most Votes Award\n\nProfile: Aoni\nInstagram: @hiroshi1975kamiya",
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                TextButton(
                    onClick =
                    {
                        isAboutContentExpanded = !isAboutContentExpanded
                    },
                    shape = MaterialTheme.shapes.medium,
                    contentPadding = PaddingValues(vertical = 4.dp, horizontal = 2.dp)
                ) {
                    Text("See more")
                }
            }
        }

        //Tab
        item {
            PrimaryTabRow(
                selectedTabIndex = tabState,
                indicator = {
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(
                            tabState,
                            matchContentSize = false
                        ), width = Dp.Unspecified
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = tabState == index,
                        onClick = { tabState = index },
                        text = {
                            Text(
                                text = title,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                    )
                }
            }
        }

        //Tab items list
        items(6) {
            AnimeVoicesItemCardList(
                voices = Voices(
                    character = Character(name = "Liu, Ryuushou"),
                    anime = AnimeDetails(title = "Taisou Zamurai"),
                    role = "Supporting"
                ),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}
@Preview
@Composable
private fun PeopleDetailsReview() {
    DenuAnimeTheme {
        Surface(modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)) {
            PersonDetailsScreenContent(modifier = Modifier.fillMaxSize(),)
        }
    }
}