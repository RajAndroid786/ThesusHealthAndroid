package com.thesushealthapp.routes

import androidx.annotation.StringRes
import com.thesushealthapp.R


//TODO add all screens here, as composable

sealed class ThesusScreen(val route: String, @StringRes val title: Int) {
    object Main : ThesusScreen(route ="main", R.string.app_name)
    object Scan : ThesusScreen(route ="scan", R.string.scan_screen_title)
    object Permission : ThesusScreen(route ="permission", R.string.request_permission)
    object Device : ThesusScreen(route ="device/{macAddress}", R.string.device_screen_title)
    object Second : ThesusScreen(route ="second", R.string.second_screen_title)
    object Third : ThesusScreen(route ="third", R.string.third_screen_title)

    companion object {
        fun fromRoute(route: String): ThesusScreen? {
            return when (route) {
                Main.route -> Main
                Scan.route -> Scan
                Permission.route -> Permission
                Device.route -> Device
                Second.route -> Second
                Third.route -> Third
                else -> null
            }
        }
    }
}