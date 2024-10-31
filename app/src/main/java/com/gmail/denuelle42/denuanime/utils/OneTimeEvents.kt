package com.gmail.denuelle42.denuanime.utils

import com.gmail.denuelle42.denuanime.navigation.NavigationScreens


sealed class OneTimeEvents {
    data class OnNavigate(val route : NavigationScreens) : OneTimeEvents()
    object OnPopBackStack : OneTimeEvents()
    data class ShowSnackbar(val snackbarEvent: SnackbarEvent)  : OneTimeEvents()
    data class ShowToast(val message : String)  : OneTimeEvents()
}