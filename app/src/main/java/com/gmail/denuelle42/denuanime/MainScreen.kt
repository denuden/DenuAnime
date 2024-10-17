package com.gmail.denuelle42.denuanime

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import kotlinx.coroutines.launch

@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
           SideBarContent()
        }
    ) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Show drawer") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            }
        ) { contentPadding ->
            // Screen content
            Box(modifier = Modifier.padding(contentPadding)){
                AppNavigation(navController)
            }
        }
    }
}

@Composable
fun SideBarContent(modifier: Modifier = Modifier) {
    ModalDrawerSheet {
        Text(stringResource(R.string.app_name), modifier = Modifier.padding(16.dp))
        HorizontalDivider()
        NavigationDrawerItem(
            label = { Text(text = "Drawer Item") },
            selected = false,
            onClick = { /*TODO*/ }
        )
        // ...other drawer items
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    DenuAnimeTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
        ) {
            SideBarContent()
        }
    }
}