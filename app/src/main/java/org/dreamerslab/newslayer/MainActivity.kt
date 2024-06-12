package org.dreamerslab.newslayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.dreamerslab.newslayer.ui.onboarding.ConfigScreen
import org.dreamerslab.newslayer.ui.theme.NewsLayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsLayerTheme {
                ConfigScreen(
                    onNavigateUp = {}
                )
            }
        }
    }
}
