package io.github.pridu.wavenavigationbarsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.pridu.wavenavigationbarsample.ui.theme.WaveNavigationBarSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaveNavigationBarSampleTheme {
                MainScreen()
            }
        }
    }
}