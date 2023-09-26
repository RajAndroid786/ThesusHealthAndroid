package com.thesushealthapp.ui.component

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.polidea.rxandroidble3.RxBleClient
import com.thesushealthapp.R
import com.thesushealthapp.view_model.ScanViewModel
import com.thesushealthapp.routes.ThesusScreen
import com.thesushealthapp.util.findActivity
import com.thesushealthapp.util.rememberLifecycleEvent

const val TAG = "ScanScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(
    modifier: Modifier,
    navController: NavHostController,
) {
    val viewModel = remember { ScanViewModel() }

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
           viewModel.refresh()
        }
    }

    Scaffold(topBar = {
        val scanning = viewModel.isScanning.collectAsState().value
        ScanTopBar(navController, scanning, viewModel)
    })
    { innerPadding ->
        BodyContent(viewModel, navController, innerPadding)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ScanTopBar(
    navController: NavHostController,
    scanning: Boolean,
    viewModel: ScanViewModel
) {
    TopAppBar(
        title = { Text(stringResource(ThesusScreen.Scan.title)) },
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
            if (viewModel.rxBleClientState.collectAsState().value == RxBleClient.State.READY)
                BuildReadyActions(scanning, viewModel)
        }
    )
}

@Composable
private fun BuildReadyActions(
    scanning: Boolean,
    viewModel: ScanViewModel
) {
    if (scanning) CircularProgressIndicator()//TODO: how we can improve it?
    IconButton(onClick = {
        if (scanning) {
            viewModel.stopScan()
        } else {
            viewModel.startScan()
        }
    }) {
        if (scanning)
            Icon(
                painter = painterResource(id = R.drawable.ic_stop),
                contentDescription = "Stop scan"
            )
        else
            Icon(
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = "Start scan"
            )
    }
}

@Composable
private fun BodyContent(viewModel: ScanViewModel,
                        navController: NavHostController,
                        innerPadding: PaddingValues) {
    Log.d(TAG, "BodyContent: ${viewModel.rxBleClientState.collectAsState().value} ")
    if (viewModel.rxBleClientState.collectAsState().value == RxBleClient.State.READY) {
        ShowContentBluetoothReady(navController, viewModel, innerPadding)
    } else {
        ShowContentBluetoothNotReady(viewModel, navController = navController, innerPadding)
    }
}


@Composable
private fun ShowContentBluetoothReady(
    navController: NavHostController,
    viewModel: ScanViewModel,
    innerPadding: PaddingValues
) {
    Log.d(TAG, "ShowContentBluetoothReady: ")
    if (viewModel.isScanning.collectAsState().value || viewModel.devicesList.collectAsState().value.isNotEmpty()) {
        ShowListOfDevices(navController, viewModel, innerPadding)
    } else {
        ShowTextAbleToStartScan(innerPadding)
    }
}

@Composable
fun ShowTextAbleToStartScan(innerPadding: PaddingValues) {
    Log.d(TAG, "ShowTextAbleToStartScan: ")
    Column (modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,) {
            Text(text = stringResource(id = R.string.start_scan));
    }
}

@Composable
private fun ShowListOfDevices(
    navController: NavHostController,
    viewModel: ScanViewModel,
    innerPadding: PaddingValues
) {
    Log.d(TAG, "ShowListOfDevices: ")
    val devicesList = viewModel.devicesList.collectAsState().value
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            items(devicesList.size) { index ->
                ItemCard(devicesList[index].name, devicesList[index].macAddress, onItemClicked = {
                    navController.navigate(ThesusScreen.Device.route.replace(
                        oldValue = "{macAddress}",
                        newValue = devicesList[index].macAddress
                    ))
                    Log.d(TAG, "onItemClicked: ${devicesList[index].name} ${devicesList[index].macAddress}")
                })
            }
        })
}

@Composable
private fun ItemCard(
    devicesName: String,
    macAddress: String,
    onItemClicked: () -> Unit
) {
    Card(shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small))
            .clickable {
                onItemClicked()
            }
    ) {
        Text(
            text = "${stringResource(id = R.string.device_name)}  " +
                    "$devicesName " +
                    "${stringResource(id = R.string.device_mac_address)} $macAddress)",
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ItemPreview() {
    ItemCard("Thermometer", "00.11.22.33.44.55", onItemClicked = {})
}

@Composable
fun ShowContentBluetoothNotReady(
    viewModel: ScanViewModel,
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    Log.d(TAG, "ShowContentBluetoothNotReady: ")
    val activity = LocalContext.current.findActivity()
    Column(
        Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = {
            executeContextActionByState(viewModel.rxBleClientState.value, navController, activity)
        }) {
            Text(text = stringResource(id = viewModel.getMessageBluetoothNotReady()))
        }
    }
}

@SuppressLint("MissingPermission")
fun executeContextActionByState(
    status: RxBleClient.State?,
    navController: NavHostController,
    activity: Activity
) {
    if (status == null) {
        return
    }
    when (status) {
        RxBleClient.State.BLUETOOTH_NOT_ENABLED -> {
            Log.d(TAG, "BLUETOOTH_NOT_ENABLED")
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activity.startActivity(enableBluetoothIntent)
        }
        RxBleClient.State.LOCATION_PERMISSION_NOT_GRANTED -> {
            Log.d(TAG, "LOCATION_PERMISSION_NOT_GRANTED")
            navController.navigate(ThesusScreen.Permission.route)
        }
        RxBleClient.State.LOCATION_SERVICES_NOT_ENABLED -> {
            Log.d(TAG, "LOCATION_SERVICES_NOT_ENABLED")
            val enableLocationServices = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            activity.startActivity(enableLocationServices)
        }
        RxBleClient.State.BLUETOOTH_NOT_AVAILABLE -> {
            Log.d(TAG, "BLUETOOTH_NOT_AVAILABLE")
        }

        else -> {
            Log.d(TAG, "BLUETOOTH_READY")}
    }
}

