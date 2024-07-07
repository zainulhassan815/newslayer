package org.dreamerslab.newslayer.feature.home

import org.dreamerslab.newslayer.common.resources.R as CommonResources
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.dreamerslab.newslayer.core.model.Category
import org.dreamerslab.newslayer.ui.theme.spacing

@Composable
fun HomeScreen(
    onNotificationsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            HomeScreenAppBar(
                onNotificationsClick = onNotificationsClick,
                onSettingsClick = onSettingsClick
            )
        }
    ) { paddingValues ->
        when (val state = uiState) {
            HomeScreenState.Error -> Unit
            HomeScreenState.Loading -> CircularProgressIndicator()
            is HomeScreenState.Success -> HomeScreenContent(
                state = state,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
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
            Text(text = stringResource(R.string.feature_home_appbar_title))
        },
        actions = {
            val borderStroke = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            OutlinedIconButton(
                onClick = onNotificationsClick,
                border = borderStroke
            ) {
                Icon(
                    painter = painterResource(CommonResources.drawable.common_resources_notifications),
                    contentDescription = stringResource(R.string.feature_home_cd_show_notifications)
                )
            }

            OutlinedIconButton(
                onClick = onSettingsClick,
                border = borderStroke
            ) {
                Icon(
                    painter = painterResource(CommonResources.drawable.common_resources_settings),
                    contentDescription = stringResource(R.string.feature_home_cd_open_settings)
                )
            }
        }
    )
}

@Composable
fun HomeScreenContent(
    state: HomeScreenState.Success,
    modifier: Modifier = Modifier
) {
    val forYouCategoryName = stringResource(R.string.feature_home_category_for_you)
    val forYouCategory = remember { Category(forYouCategoryName) }

    val categories = remember(state) {
        with(state) { listOf(forYouCategory) + followedCategories + otherCategories }
    }
    val pagerState = rememberPagerState { categories.size }

    Column(
        modifier = modifier,
    ) {
        CategoryTabs(
            modifier = Modifier.fillMaxWidth(),
            categories = categories,
            pagerState = pagerState
        )

        HorizontalPager(
            state = pagerState,
            key = { it },
            beyondViewportPageCount = 1, // Load next page in advance
        ) { page ->
            CategoryList(
                viewModel = hiltViewModel(
                    creationCallback = { factory: CategoryListViewModel.CategoryListViewModelFactory ->
                        val selectedCategory = categories[page]
                        val data = when {
                            selectedCategory == forYouCategory -> state.followedCategories
                            else -> listOf(selectedCategory)
                        }
                        factory.create(categories = data)
                    },
                    key = categories[page].name
                )
            )
        }
    }
}

@Composable
fun CategoryTabs(
    categories: List<Category>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = modifier,
        edgePadding = MaterialTheme.spacing.large,
    ) {
        categories.forEachIndexed { idx, category ->
            Tab(
                selected = pagerState.currentPage == idx,
                onClick = {
                    scope.launch { pagerState.scrollToPage(idx) }
                },
                text = { Text(category.name.capitalize(Locale.current)) }
            )
        }
    }
}
