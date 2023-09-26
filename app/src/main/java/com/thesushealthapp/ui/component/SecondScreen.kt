package com.thesushealthapp.ui.component

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.thesushealthapp.R
import com.thesushealthapp.routes.ThesusScreen

//TODO to remove only for the test the navigation component
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(navController: NavController) {

    val context = LocalContext.current
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(stringResource(ThesusScreen.Second.title)) },
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
        BodyContent(navController, innerPadding)
    }


}

@Composable
private fun BodyContent(navController: NavController,
                        innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Second screen")
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Back to first screen")
        }
        Button(onClick = {
            navController.navigate(ThesusScreen.Third.route)
        }) {
            Text(text = "Go to third screen")
        }
    }
}