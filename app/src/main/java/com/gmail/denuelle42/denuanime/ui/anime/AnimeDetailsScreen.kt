package com.gmail.denuelle42.denuanime.ui.anime

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.gmail.denuelle42.denuanime.navigation.NavigationScreens
import com.gmail.denuelle42.denuanime.ui.anime.components.AiredInfoSection
import com.gmail.denuelle42.denuanime.ui.anime.components.AnimeHeader
import com.gmail.denuelle42.denuanime.ui.anime.components.BroadcastInfoSection
import com.gmail.denuelle42.denuanime.ui.anime.components.OtherListingsSection
import com.gmail.denuelle42.denuanime.ui.anime.components.SynopsisSection
import com.gmail.denuelle42.denuanime.ui.common.chips.GenreChips
import com.gmail.denuelle42.denuanime.ui.common.dialog.FullScreenDialog
import com.gmail.denuelle42.denuanime.ui.common.skeleton.SkeletonAnimeDetailsScreen
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.AsyncImageWithBackgroundPalette
import com.gmail.denuelle42.denuanime.utils.ComposableLifecycle
import com.gmail.denuelle42.denuanime.utils.ObserveAsEvents
import com.gmail.denuelle42.denuanime.utils.OneTimeEvents
import com.gmail.denuelle42.denuanime.utils.SnackBarController
import com.gmail.denuelle42.denuanime.utils.orEmpty
import kotlinx.coroutines.launch

@Composable
fun AnimeDetailsScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (NavigationScreens) -> Unit,
    id: Int,
    viewModel: AnimeViewModel = hiltViewModel()
) {

    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var shouldShowFullScreenImage by rememberSaveable { mutableStateOf(false) }
    var backgroundColor by remember { mutableIntStateOf(0) }
    // Animate the background color
    val animatedColor by animateColorAsState(
        targetValue = Color(backgroundColor),
        label = "Background Color"
    )


    ObserveAsEvents(flow = viewModel.channel) { event ->
        when (event) {
            is OneTimeEvents.OnNavigate -> onNavigate(event.route)
            OneTimeEvents.OnPopBackStack -> onPopBackStack()
            is OneTimeEvents.ShowSnackbar -> {
                scope.launch {
                    SnackBarController.sendEvent(event.snackbarEvent)
                }
            }
            is OneTimeEvents.ShowToast -> {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
            is OneTimeEvents.ShowInputError -> {

            }
        }
    }

    ComposableLifecycle { _, lifecycleEvent ->
        when (lifecycleEvent) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.onEvent(AnimeEvents.OnGetAnimeFullById(id))
            }

            else -> Unit
        }
    }


    AnimeDetailsScreenContent(uiState = uiState, onEnlargeImage = {
        shouldShowFullScreenImage = true // show full screen image
    })

    FullScreenDialog(
        showDialog = shouldShowFullScreenImage,
        onDismissRequest = { shouldShowFullScreenImage = false }) {
        Box(modifier = Modifier.background(color = animatedColor)) {
            AsyncImageWithBackgroundPalette(
                model = uiState.animeDetails?.images?.jpg?.large_image_url.orEmpty(),
                onEnlargeImage = {
                    shouldShowFullScreenImage = false
                }, //since this is from fullscreen, make it a close button instead of enlarge
                enlargeImageIcon = Icons.Default.Close,
                onPaletteBuilderSuccess = { backgroundColor = it },
                modifier = Modifier.matchParentSize()
            )
        }
    }
}

@SuppressLint("ResourceAsColor")
@Composable
fun AnimeDetailsScreenContent(
    modifier: Modifier = Modifier,
    uiState: AnimeState,
    onEnlargeImage: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val animeDetails = uiState.animeDetails

    AnimatedVisibility(
        visible = !uiState.isGetAnimeFullByIdLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(modifier = modifier.verticalScroll(scrollState)) {
            AnimeHeader(
                image = animeDetails?.images?.jpg?.large_image_url.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth(),
                title = animeDetails?.title.orEmpty("---"),
                titleEn = animeDetails?.title_english.orEmpty("---"),
                titleJp = animeDetails?.title_japanese.orEmpty("---"),
                onEnlargeImage = onEnlargeImage
            )

            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                //Genres + Themes + Demographics
                val genres =
                    animeDetails?.genres.orEmpty() + animeDetails?.themes.orEmpty() + animeDetails?.demographics.orEmpty()
                GenreChips(genres = genres)

                //Synopsis
                SynopsisSection(
                    synopsis = animeDetails?.synopsis,
                    modifier = Modifier.padding(top = 8.dp)
                )

                //Info about Airing
                AiredInfoSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    context = context,
                    animeDetails = animeDetails ?: AnimeDetails()
                )

                //Broadcast Info
                BroadcastInfoSection(
                    animeDetails = animeDetails ?: AnimeDetails(),
                    modifier = Modifier.padding(top = 8.dp)
                )

                //Other listings
                OtherListingsSection(
                    animeDetails = animeDetails ?: AnimeDetails(),
                    modifier = Modifier.padding(top = 8.dp, bottom = 50.dp)
                )
            }
        }
    }


    AnimatedVisibility(
        visible = uiState.isGetAnimeFullByIdLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        SkeletonAnimeDetailsScreen()
    }
}


@Preview
@Composable
private fun AnimeDetailsScreenPreview() {
    DenuAnimeTheme {
        Surface(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)
        ) {
            AnimeDetailsScreenContent(
                uiState = AnimeState(
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
            ) {}
        }
    }
}