package org.dreamerslab.newslayer

import org.dreamerslab.newslayer.common.resources.R as CommonResources
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.dreamerslab.newslayer.feature.articledetails.ArticleDetailsPage
import org.dreamerslab.newslayer.feature.home.HomeScreen
import org.dreamerslab.newslayer.feature.onboarding.OnboardingScreen
import org.dreamerslab.newslayer.feature.search.SearchScreen

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
            NavigationBarWrapper(
                controller = controller,
                selected = Home
            ) {
                HomeScreen(
                    onNotificationsClick = {
                        // TODO: Navigate to Notifications screen
                    },
                    onSettingsClick = {
                        // TODO: Navigate to Settings
                    },
                    onArticleClick = {
                        controller.navigate(ArticleDetails(it.id))
                    }
                )
            }
        }

        composable<Search> {
            NavigationBarWrapper(
                controller = controller,
                selected = Search
            ) {
                SearchScreen(
                    onArticleClick = {
                        controller.navigate(ArticleDetails(it.id))
                    }
                )
            }
        }

        composable<ArticleDetails> {
            ArticleDetailsPage(
                onNavigateUp = controller::navigateUp
            )
        }
    }
}

data class NavigationBarDestinationItem(
    val destination: TopLevelDestination,
    @DrawableRes val iconRes: Int,
    @StringRes val contentDescriptionRes: Int,
)

private val topLevelDestinations = listOf(
    NavigationBarDestinationItem(
        destination = Home,
        iconRes = CommonResources.drawable.common_resources_home_filled,
        contentDescriptionRes = R.string.navigation_bar_cd_icon_home
    ),
    NavigationBarDestinationItem(
        destination = Search,
        iconRes = CommonResources.drawable.common_resources_search,
        contentDescriptionRes = R.string.navigation_bar_cd_icon_search
    )
)

@Composable
private fun NavigationBarWrapper(
    controller: NavHostController,
    selected: TopLevelDestination,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    NewsLayerNavigationBar(
        selected = selected,
        onDestinationClick = {
            controller.navigate(it)
            {
                popUpTo(controller.graph.startDestinationId) {
                    inclusive = true
                    saveState = true
                }

                launchSingleTop = true
                restoreState = true
            }
        },
        modifier = modifier,
        content = content
    )
}

@Composable
private fun NewsLayerNavigationBar(
    selected: TopLevelDestination,
    onDestinationClick: (destination: TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier,
    destinations: List<NavigationBarDestinationItem> = topLevelDestinations,
    content: @Composable () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(
                modifier = Modifier.shadow(
                    elevation = 12.dp,
                    clip = false,
                ),
                tonalElevation = 0.dp,
            ) {
                destinations.forEach {
                    NavigationBarItem(
                        selected = selected == it.destination,
                        onClick = { onDestinationClick(it.destination) },
                        icon = {
                            Icon(
                                painter = painterResource(it.iconRes),
                                contentDescription = stringResource(it.contentDescriptionRes)
                            )
                        }
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .consumeWindowInsets(it)
        ) {
            content()
        }
    }
}
