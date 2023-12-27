package vn.doitsolutions.quickz.navigation

import vn.doitsolutions.quickz.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.home, "Home")
    object Quiz : NavigationItem("quiz", R.drawable.gameboy, "Quiz")
    object Profile : NavigationItem("profile", R.drawable.frame, "Profile")
}
