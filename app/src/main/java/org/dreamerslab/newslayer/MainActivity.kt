package org.dreamerslab.newslayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.dreamerslab.newslayer.core.model.DarkThemeConfig
import org.dreamerslab.newslayer.core.model.UserData
import org.dreamerslab.newslayer.ui.theme.NewsLayerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: MainActivityState by mutableStateOf(MainActivityState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state
                    .onEach { uiState = it }
                    .collect()
            }
        }

        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                MainActivityState.Loading -> true
                is MainActivityState.Success -> false
            }
        }

        enableEdgeToEdge()

        setContent {
            val darkTheme = shouldUseDarkTheme(uiState)

            // Update the edge to edge configuration to match the theme
            // This is the same parameters as the default enableEdgeToEdge call, but we manually
            // resolve whether or not to show dark theme using state, since it can be different
            // than the configuration's dark theme value based on the user preference.
            DisposableEffect(darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    ) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim,
                        darkScrim,
                    ) { darkTheme },
                )
                onDispose {}
            }

            when (val state = uiState) {
                MainActivityState.Loading -> Unit
                is MainActivityState.Success -> MainActivityContent(
                    userData = state.userData
                )
            }
        }
    }
}

@Composable
fun MainActivityContent(
    userData: UserData,
) {
    NewsLayerTheme(
        darkTheme = shouldUseDarkTheme(userData)
    ) {
        NewsLayerNavigation(
             startDestination = getStartDestination(userData)
        )
    }
}

@Composable
fun shouldUseDarkTheme(
    state: MainActivityState
): Boolean = when (state) {
    MainActivityState.Loading -> false
    is MainActivityState.Success -> shouldUseDarkTheme(state.userData)
}

@Composable
fun shouldUseDarkTheme(
    data: UserData
): Boolean = when (data.darkThemeConfig) {
    DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
    DarkThemeConfig.DARK -> true
    DarkThemeConfig.LIGHT -> false
}

fun getStartDestination(
    userData: UserData
): Destination = when {
    userData.shouldHideOnboarding -> Home
    else -> OnBoarding
}

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)
