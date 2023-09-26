@file:OptIn(ExperimentalMaterial3Api::class)

package com.thesushealthapp;

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.thesushealthapp.routes.ThesusScreen
import com.thesushealthapp.ui.component.DeviceScreen
import com.thesushealthapp.ui.component.MainScreen
import com.thesushealthapp.ui.component.PermissionScreen
import com.thesushealthapp.ui.component.ScanScreen
import com.thesushealthapp.ui.component.SecondScreen
import com.thesushealthapp.ui.component.ThirdScreen
import com.thesushealthapp.ui.component.getPermissionsState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ThesusApp(
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = ThesusScreen.fromRoute(
        backStackEntry?.destination?.route ?: ThesusScreen.Main.route
    )

    val multiplePermissionsState = getPermissionsState()

    val initialScreen = if (multiplePermissionsState.allPermissionsGranted) {
        ThesusScreen.Main
    } else {
        ThesusScreen.Permission
    }
            NavHost(
            navController = navController,
            startDestination = initialScreen.route,

        ) {
            composable(route = ThesusScreen.Main.route) {
                MainScreen(navController = navController, modifier = Modifier)
            }
            composable(route = ThesusScreen.Scan.route) {
                ScanScreen(modifier = Modifier, navController = navController)
            }
            composable(route = ThesusScreen.Permission.route) {
                PermissionScreen(navController = navController, modifier = Modifier)
            }
            composable(route = ThesusScreen.Second.route) {
                SecondScreen(navController = navController)
            }
                composable(route = ThesusScreen.Device.route) {
                        navBackStackEntry ->
                        val macAddress = navBackStackEntry.arguments?.getString("macAddress")
                        macAddress?.let {
                            DeviceScreen(macAddress = it, navController = navController, modifier = Modifier)
                        }

            }
            composable(route = ThesusScreen.Third.route) {
                ThirdScreen(navController = navController)
            }
        }
}


