package com.thesushealthapp.ui.component

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.thesushealthapp.R
import com.thesushealthapp.routes.ThesusScreen


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PermissionScreen(
    modifier: Modifier,
    navController: NavHostController,
) {
    val multiplePermissionsState = getPermissionsState()

    val context = LocalContext.current
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(stringResource(ThesusScreen.Permission.title)) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            navigationIcon = {
                if (navController.previousBackStackEntry != null) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            },
            actions = {
                IconButton(onClick = {
                    Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
                }
            }
        ) })
    { innerPadding ->
        BodyContent(multiplePermissionsState, navController, innerPadding)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun BodyContent(
    multiplePermissionsState: MultiplePermissionsState,
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    if (multiplePermissionsState.allPermissionsGranted) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(id = R.string.permission_granted))
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate(ThesusScreen.Scan.route) }) {
                Text(text = stringResource(id = R.string.synchronize_devices))
            }
        }
    } else {
        Column(
            Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.request_permission_explain)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }) {
                Text(stringResource(R.string.request_permission))
            }
        }
    }
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun getPermissionsState(): MultiplePermissionsState {
    val listPermission = mutableListOf<String>()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        listPermission.add(Manifest.permission.BLUETOOTH_SCAN)
        listPermission.add(Manifest.permission.BLUETOOTH_CONNECT)
        listPermission.add(Manifest.permission.BLUETOOTH_ADMIN)
        listPermission.add(Manifest.permission.BLUETOOTH)
    } else {
        listPermission.add(Manifest.permission.ACCESS_FINE_LOCATION)
        listPermission.add(Manifest.permission.BLUETOOTH_ADMIN)
        listPermission.add(Manifest.permission.BLUETOOTH_ADMIN)
        listPermission.add(Manifest.permission.BLUETOOTH)
    }

    val multiplePermissionsState = rememberMultiplePermissionsState(listPermission);
    return multiplePermissionsState
}