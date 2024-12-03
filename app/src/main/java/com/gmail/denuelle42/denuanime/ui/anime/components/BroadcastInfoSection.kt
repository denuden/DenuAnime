package com.gmail.denuelle42.denuanime.ui.anime.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gmail.denuelle42.denuanime.data.remote.models.BaseImages
import com.gmail.denuelle42.denuanime.data.remote.models.ImageType
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Aired
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Broadcast
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.External
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Genre
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Licensor
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Producer
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Streaming
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Studio
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.ThemeSong
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Trailer
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.orEmpty

@Composable
fun BroadcastInfoSection(modifier: Modifier = Modifier, animeDetails: AnimeDetails) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.height(IntrinsicSize.Min)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary),
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Event,
                    contentDescription = null,
                    modifier = Modifier
                        .size(34.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${"Broadcast".uppercase()}\non\n${animeDetails.broadcast?.string.orEmpty("-----")}",
                    color = MaterialTheme.colorScheme.onTertiary,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                )
            }
        }


        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f)
        ) {

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 120.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Movie,
                            contentDescription = null,
                        )
                        Text(
                            text = "${animeDetails.episodes ?: 0} EPISODES",
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AccessTime,
                            contentDescription = null,
                        )
                        Text(
                            text = animeDetails.duration.orEmpty("----").uppercase(),
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                        )
                    }
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 120.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Category,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "${animeDetails.type.orEmpty("--").uppercase()}/${animeDetails.source.orEmpty("--").uppercase()}",
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 13.sp,
                    )
                    Text(
                        text = animeDetails.rating.orEmpty("---").uppercase(),
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 13.sp,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun BroadcastInfoSectionPreview() {
    DenuAnimeTheme {
        BroadcastInfoSection(
            animeDetails = AnimeDetails(
                mal_id = 37999,
                images = ImageType(
                    jpg = BaseImages(
                        image_url = "https://cdn.myanimelist.net/images/anime/1295/106551.jpg",
                        large_image_url = "https://cdn.myanimelist.net/images/anime/1295/106551l.jpg"
                    )
                ),
                trailer = Trailer(
                    embed_url = "https://www.youtube.com/embed/Ti2kJ-GYO68?enablejsapi=1&wmode=opaque&autoplay=1",
                    images = BaseImages(
                        image_url = "https://img.youtube.com/vi/Ti2kJ-GYO68/default.jpg",
                        small_image_url = "https://img.youtube.com/vi/Ti2kJ-GYO68/sddefault.jpg",
                        medium_image_url = "https://img.youtube.com/vi/Ti2kJ-GYO68/mqdefault.jpg",
                        large_image_url = "https://img.youtube.com/vi/Ti2kJ-GYO68/hqdefault.jpg",
                        maximum_image_url = "https://img.youtube.com/vi/Ti2kJ-GYO68/maxresdefault.jpg"
                    )
                ),
                title = "Kaguya-sama wa Kokurasetai: Tensai-tachi no Renai Zunousen",
                title_english = "Kaguya-sama: Love is War",
                title_japanese = "かぐや様は告らせたい～天才たちの恋愛頭脳戦～",
                type = "TV",
                source = "Manga",
                episodes = 12,
                status = "Finished Airing",
                airing = false,
                aired = Aired(
                    from = "2019-01-12T00:00:00+00:00",
                    to = "2019-03-30T00:00:00+00:00",
                    string = "Jan 12, 2019 to Mar 30, 2019"
                ),
                duration = "25 min per ep",
                rating = "PG-13 - Teens 13 or older",
                score = 8.4,
                scored_by = 1131796,
                rank = 193,
                popularity = 51,
                members = 1801547,
                favorites = 40412,
                synopsis = "At the renowned Shuchiin Academy, Miyuki Shirogane and Kaguya Shinomiya are the student body's top representatives. Ranked the top student in the nation and respected by peers and mentors alike, Miyuki serves as the student council president. Alongside him, the vice president Kaguya—eldest daughter of the wealthy Shinomiya family—excels in every field imaginable. They are the envy of the entire student body, regarded as the perfect couple.\n\nHowever, despite both having already developed feelings for the other, neither are willing to admit them. The first to confess loses, will be looked down upon, and will be considered the lesser. With their honor and pride at stake, Miyuki and Kaguya are both equally determined to be the one to emerge victorious on the battlefield of love!\n\n[Written by MAL Rewrite]",
                background = "An edited version of the series received a rebroadcast starting July 10, 2014. 22 episodes of the original series were combined into eleven 46-minute long episodes with some scenes being slightly extended. Psycho-Pass aired on Fuji Television's noitaminA block. In the 2013 Newtype Anime Awards it was voted as fourth best title of the year. Its 11th episode was awarded \\\"Best Episode\\\" in the Noitamina 10th anniversary fan vote. It has spawned several video-game spin-offs, a novel series and a manga series as well.",
                season = "winter",
                year = 2019,
                broadcast = Broadcast(
                    day = "Saturdays",
                    time = "23:30",
                    timezone = "Asia/Tokyo",
                    string = "Saturdays at 23:30 (JST)"
                ),
                producers = listOf(
                    Producer(
                        name = "Aniplex",
                        url = "https://myanimelist.net/anime/producer/17/Aniplex"
                    ),
                    Producer(
                        name = "Mainichi Broadcasting System",
                        url = "https://myanimelist.net/anime/producer/143/Mainichi_Broadcasting_System",
                    ),
                    Producer(
                        name = "Magic Capsule",
                        url = "https://myanimelist.net/anime/producer/306/Magic_Capsule",
                    )
                ),
                licensors = listOf(
                    Licensor(
                        name = "Aniplex of America",
                        url = "https://myanimelist.net/anime/producer/493/Aniplex_of_America"
                    )
                ),
                studios = listOf(
                    Studio(
                        name = "A-1 Pictures",
                        url = "https://myanimelist.net/anime/producer/1/A-1_Pictures"
                    )
                ),
                genres = listOf(
                    Genre(name = "Comedy"),
                    Genre(name = "Romance"),
                ),
                themes = listOf(
                    Genre(name = "School")
                ),
                demographics = listOf(
                    Genre(name = "Seinen")
                ),
                theme = ThemeSong(
                    openings = listOf("\\\"Love Dramatic feat. Rikka Ihara (ラブ・ドラマティック feat.伊原六花)\\\" by Masayuki Suzuki (鈴木雅之) (eps 1-12)"),
                    endings = listOf(
                        "1: \\\"Sentimental Crisis (センチメンタルクライシス) \\\" by halca (eps 1-2,4-12)",
                        "2: \\\"Chikatto Chika Chika♡ (チカっとチカ千花っ♡)\\\" by Chika Fujiwara (Konomi Kohara (eps 3)"
                    )
                ),
                external = listOf(
                    External(
                        name = "Official Site",
                        url = "https://kaguya.love/1st/"
                    ),
                    External(
                        name = "Official Site",
                        url = "https://kaguyasama-anime.com/"
                    ),
                    External(
                        name = "@anime_kaguya",
                        url = "https://twitter.com/anime_kaguya"
                    ),
                    External(
                        name = "AniDB",
                        url = "https://anidb.net/perl-bin/animedb.pl?show=anime&aid=14111"
                    ),
                    External(
                        name = "ANN",
                        url = "https://www.animenewsnetwork.com/encyclopedia/anime.php?id=21401"
                    ),
                    External(
                        name = "Wikipedia",
                        url = "https://ja.wikipedia.org/wiki/%E3%81%8B%E3%81%90%E3%82%84%E6%A7%98%E3%81%AF%E5%91%8A%E3%82%89%E3%81%9B%E3%81%9F%E3%81%84%E3%80%9C%E5%A4%A9%E6%89%8D%E3%81%9F%E3%81%A1%E3%81%AE%E6%81%8B%E6%84%9B%E9%A0%AD%E8%84%B3%E6%88%A6%E3%80%9C#%E3%82%A2%E3%83%8B%E3%83%A1"
                    ),
                    External(
                        name = "Syoboi",
                        url = "https://cal.syoboi.jp/tid/5138"
                    ),
                ),
                streaming = listOf(
                    Streaming(
                        name = "Crunchyroll",
                        url = "http://www.crunchyroll.com/series-277391"
                    ),
                    Streaming(
                        name = "Netflix",
                        url = "https://www.netflix.com/"
                    )
                )
            )
        )
    }
}
