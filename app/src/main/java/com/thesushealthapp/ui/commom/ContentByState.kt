package com.thesushealthapp.ui.commom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

sealed class ContentByState {
    object Loading : ContentByState()
    object Loaded : ContentByState()
    object Error : ContentByState()
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(onTryAgain: () -> Unit) {
    Text(text = "Error onTryAgain ",
        Modifier.clickable {
            onTryAgain() } )
}

@Composable
fun ReadyScreen(content: @Composable() () -> Unit) {
    content()
}