package com.gmail.denuelle42.denuanime

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gmail.denuelle42.denuanime.navigation.MainScreens
import com.gmail.denuelle42.denuanime.navigation.NavigationScreens
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SideBarContent(onNavigate = {
                scope.launch {
                    drawerState.close()
                }
                navController.navigate(it) {
                    launchSingleTop = true
                }
            })
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBarContent(onClickNavigationMenu = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                })
            },
        ) { contentPadding ->
            // Screen content
            Box(modifier = Modifier.padding(contentPadding)) {
                AppNavigation(navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarContent(modifier: Modifier = Modifier, onClickNavigationMenu : () -> Unit,) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        title = {
            Text("DenuAnime")
        },
        navigationIcon = {
            IconButton(onClick = {
               onClickNavigationMenu()
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu Bar"
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search)
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(R.string.favorite)
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun SideBarContent(onNavigate: (NavigationScreens) -> Unit) {
    var categoryState by remember { mutableStateOf(false) }
    ModalDrawerSheet {
        Box(
            modifier = Modifier
                .background(Color.Gray.copy(alpha = 0.2f))
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.app_name),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                letterSpacing = 5.sp
            )
        }
        HorizontalDivider(modifier = Modifier.padding(bottom = 12.dp))

        Column(modifier = Modifier
            .animateContentSize()
            .padding(horizontal = 8.dp)) {
            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp
                    )
                },
                selected = true,
                onClick = { onNavigate(MainScreens.HomeNavigation) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = stringResource(R.string.home)
                    )
                }
            )
            NavigationDrawerItem(
                label = {
                    Text(
                        text = stringResource(R.string.favorites),
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp
                    )
                },
                selected = false,
                onClick = { onNavigate(MainScreens.FavoritesNavigation) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = stringResource(R.string.favorites)
                    )
                }
            )
            NavigationDrawerItem(
                label = {
                    Text(
                        text = stringResource(R.string.history),
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp
                    )
                },
                selected = false,
                onClick = { /*TODO*/ },
                icon = {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = stringResource(R.string.history)
                    )
                }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
            NavigationDrawerItem(
                label = {
                    Text(
                        text = stringResource(R.string.categories),
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp
                    )
                },
                selected = false,
                onClick = { categoryState = !categoryState },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Category,
                        contentDescription = stringResource(R.string.categories)
                    )
                },
                badge = {
                    Icon(
                        imageVector = if (categoryState) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = stringResource(R.string.categories)
                    )
                }
            )

            /**
             * For Categories sub navigation drawer
             */
            if (categoryState) {
                Categories()
            }
        }
    }
}

@Composable
fun Categories(modifier: Modifier = Modifier) {
    var animeSubCategoriesState by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .animateContentSize()
            .padding(start = 30.dp)
    ) {
        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(R.string.anime),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 18.sp
                )
            },
            selected = false,
            onClick = { animeSubCategoriesState = !animeSubCategoriesState },
            icon = {
                Icon(
                    imageVector = Icons.Default.FiberManualRecord,
                    contentDescription = stringResource(R.string.anime)
                )
            },
            badge = {
                Icon(
                    imageVector = if (animeSubCategoriesState) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = stringResource(R.string.anime)
                )
            }
        )
        /**
         * Anime Sub Categories
         */
        if (animeSubCategoriesState) {
            AnimeSubCategories()
        }

        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(R.string.characters),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 18.sp
                )
            },
            selected = false,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Default.FiberManualRecord,
                    contentDescription = stringResource(R.string.characters)
                )
            },
            badge = {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = stringResource(R.string.characters)
                )
            }
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(R.string.voice_actor_actress),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 18.sp
                )
            },
            selected = false,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Default.FiberManualRecord,
                    contentDescription = stringResource(R.string.voice_actor_actress)
                )
            },
            badge = {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = stringResource(R.string.voice_actor_actress)
                )
            }
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(R.string.manga),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 18.sp
                )
            },
            selected = false,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Default.FiberManualRecord,
                    contentDescription = stringResource(R.string.manga)
                )
            },
            badge = {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = stringResource(R.string.manga)
                )
            }
        )
    }
}

@Composable
fun AnimeSubCategories(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(start = 28.dp)
        ) {
            VerticalDivider(modifier = Modifier.fillMaxHeight())
            Spacer(modifier = Modifier.padding(end = 12.dp))

            Column {
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = stringResource(R.string.episodes),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp
                        )
                    },
                    selected = false,
                    onClick = { /*TODO*/ },
                )
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = stringResource(R.string.seasons),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp
                        )
                    },
                    selected = false,
                    onClick = { /*TODO*/ },
                )
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = stringResource(R.string.schedules),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp
                        )
                    },
                    selected = false,
                    onClick = { /*TODO*/ },
                )
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = stringResource(R.string.genre),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp
                        )
                    },
                    selected = false,
                    onClick = { /*TODO*/ },
                )
            }

        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    DenuAnimeTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
        ) {
            TopAppBarContent(onClickNavigationMenu = {

            })
        }
    }
}