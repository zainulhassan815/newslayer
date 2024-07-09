package org.dreamerslab.newslayer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import org.dreamerslab.newslayer.feature.home.HomeScreen
import org.dreamerslab.newslayer.feature.onboarding.OnboardingScreen
import org.dreamerslab.newslayer.feature.search.SearchScreen

@Serializable
sealed interface Destination

@Serializable
data object OnBoarding : Destination

@Serializable
data object Home : Destination

@Serializable
data object Search : Destination

@Composable
fun NewsLayerNavigation(
    modifier: Modifier = Modifier,
    controller: NavHostController = rememberNavController(),
    startDestination: Destination = OnBoarding,
) {
    NavHost(
        modifier = modifier,
        navController = controller,
        startDestination = startDestination
    ) {
        composable<OnBoarding> {
            OnboardingScreen()
        }

        composable<Home> {
            HomeScreen(
                onNotificationsClick = {
                    // TODO: Navigate to Notifications screen
                },
                onSettingsClick = {
                    // TODO: Navigate to Settings
                }
            )
        }

        composable<Search> {
            SearchScreen(
                onArticleClick = {
                    // TODO: Open ArticleDetails page
                }
            )
        }
    }
}
