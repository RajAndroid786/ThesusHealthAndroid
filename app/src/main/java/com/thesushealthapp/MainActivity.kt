package com.thesushealthapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.thesushealthapp.ui.theme.ThesusHealthAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThesusHealthAppTheme {
                ThesusApp()
            }
        }
    }
}
