package org.dreamerslab.newslayer.feature.home

import org.dreamerslab.newslayer.common.resources.R as CommonResources
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.dreamerslab.newslayer.ui.theme.spacing

@Composable
fun HomeScreen(
    onNotificationsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            HomeScreenAppBar(
                onNotificationsClick = onNotificationsClick,
                onSettingsClick = onSettingsClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            val list = remember { listOf("sports", "business", "education") }
            val pagerState = rememberPagerState { list.size }
            var select by remember { mutableIntStateOf(0) }

            LaunchedEffect(select) {
                pagerState.animateScrollToPage(select)
            }

            ScrollableTabRow(
                selectedTabIndex = select,
                modifier = Modifier.fillMaxWidth(),
                edgePadding = MaterialTheme.spacing.large,
            ) {
                list.forEachIndexed { idx, category ->
                    Tab(
                        selected = select == idx,
                        onClick = { select = idx },
                        text = { Text(category.capitalize(Locale.current)) }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                key = { it },
                beyondViewportPageCount = 2, // Loads next two pages in advance
            ) { page ->
                CategoryList(
                    viewModel = hiltViewModel(
                        creationCallback = { factory: CategoryListViewModel.CategoryListViewModelFactory ->
                            factory.create(list[page])
                        },
                        key = list[page]
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenAppBar(
    onNotificationsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = "Home")
        },
        actions = {
            val borderStroke = BorderStroke(1.dp,MaterialTheme.colorScheme.outlineVariant)
            OutlinedIconButton(
                onClick = onNotificationsClick,
                border = borderStroke
            ) {
                Icon(
                    painter = painterResource(CommonResources.drawable.common_resources_notifications),
                    contentDescription = "Show Notifications"
                )
            }

            OutlinedIconButton(
                onClick = onSettingsClick,
                border = borderStroke
            ) {
                Icon(
                    painter = painterResource(CommonResources.drawable.common_resources_settings),
                    contentDescription = "Open Settings"
                )
            }
        }
    )
}
