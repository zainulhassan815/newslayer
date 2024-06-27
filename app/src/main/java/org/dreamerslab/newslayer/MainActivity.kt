package org.dreamerslab.newslayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import org.dreamerslab.newslayer.feature.onboarding.ConfigScreen
import org.dreamerslab.newslayer.feature.onboarding.OnboardingScreen
import org.dreamerslab.newslayer.ui.theme.NewsLayerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsLayerTheme {
                OnboardingScreen(
                    onContinueClick = {}
                )
            }
        }
    }
}
