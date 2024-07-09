package org.dreamerslab.newslayer.feature.search

import org.dreamerslab.newslayer.common.resources.R as CommonResources
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.dreamerslab.newslayer.core.model.NewsArticle
import org.dreamerslab.newslayer.ui.components.ErrorMessage
import org.dreamerslab.newslayer.ui.components.ListNewsCard
import org.dreamerslab.newslayer.ui.components.PrimaryButton
import org.dreamerslab.newslayer.ui.theme.spacing

@Composable
fun SearchScreen(
    onArticleClick: (article: NewsArticle) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier,
        topBar = { SearchScreenAppBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            val query by viewModel.query.collectAsStateWithLifecycle()

            SearchField(
                query = query,
                onChange = viewModel::updateQuery,
                onSearchAction = {},
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.spacing.large,
                    vertical = MaterialTheme.spacing.small,
                ),
            )

            val state by viewModel.state.collectAsStateWithLifecycle()

            when (state) {
                SearchScreenState.Initial -> Unit
                is SearchScreenState.Data -> SearchResultContent(
                    pagingItemsFlow = (state as SearchScreenState.Data).pagingDataFlow,
                    currentQuery = { query },
                    onArticleClick = onArticleClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(R.string.feature_search_app_bar_title))
        }
    )
}

@Composable
private fun SearchField(
    query: String,
    onChange: (query: String) -> Unit,
    onSearchAction: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        TextField(
            value = query,
            onValueChange = onChange,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = stringResource(R.string.feature_search_search_field_placeholder))
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(CommonResources.drawable.common_resources_search),
                    contentDescription = stringResource(R.string.feature_search_search_field_cd_search_icon)
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { onChange("") },
                    modifier = Modifier.padding(end = MaterialTheme.spacing.extraSmall)
                ) {
                    Icon(
                        painter = painterResource(CommonResources.drawable.common_resources_cancel),
                        contentDescription = stringResource(R.string.feature_search_search_field_cd_clear_search_icon)
                    )
                }
            },
            shape = CircleShape,
            singleLine = true,
            keyboardActions = KeyboardActions(
                onSearch = { onSearchAction() }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            )
        )
    }
}

@Composable
private fun SearchResultContent(
    pagingItemsFlow: Flow<PagingData<NewsArticle>>,
    currentQuery: () -> String,
    onArticleClick: (article: NewsArticle) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagingItems = pagingItemsFlow.collectAsLazyPagingItems()

    val isLoading = pagingItems.loadState.refresh is LoadState.Loading
    val hasError = pagingItems.loadState.refresh is LoadState.Error
    val isResultEmpty =
        pagingItems.loadState.refresh is LoadState.NotLoading
                && pagingItems.itemSnapshotList.isEmpty()

    when {
        isLoading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        hasError -> ErrorMessage(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.large),
            title = stringResource(R.string.feature_search_search_result_error_title),
            subtitle = stringResource(R.string.feature_search_search_result_error_msg),
            icon = {
                Icon(
                    painter = painterResource(CommonResources.drawable.common_resources_report),
                    contentDescription = null
                )
            },
            action = {
                PrimaryButton(
                    label = stringResource(R.string.feature_search_search_result_try_again_btn_lbl),
                    onClick = { pagingItems.refresh() }
                )
            }
        )

        isResultEmpty -> ErrorMessage(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.large),
            title = stringResource(R.string.feature_search_search_result_empty_result_error_title),
            subtitle = stringResource(
                id = R.string.feature_search_search_result_empty_result_error_msg,
                currentQuery()
            ),
            icon = {
                Icon(
                    painter = painterResource(CommonResources.drawable.common_resources_search),
                    contentDescription = null
                )
            }
        )

        else -> ArticlesListScaffold(
            pagingItems = pagingItems,
            onArticleClick = onArticleClick,
            modifier = modifier,
        )
    }
}

@Composable
private fun ArticlesListScaffold(
    pagingItems: LazyPagingItems<NewsArticle>,
    onArticleClick: (article: NewsArticle) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val showFloatingActionButton by remember(listState) {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current

    LaunchedEffect(showFloatingActionButton) {
        if (showFloatingActionButton) {
            focusManager.clearFocus()
            keyboardManager?.hide()
        }
    }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.systemBars.exclude(WindowInsets.statusBars),
        floatingActionButton = {
            if (showFloatingActionButton) FloatingActionButton(
                onClick = {
                    scope.launch { listState.scrollToItem(0) }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null
                )
            }
        }
    ) { padding ->
        ArticlesList(
            pagingItems = pagingItems,
            onArticleClick = onArticleClick,
            listState = listState,
            contentPadding = padding
        )
    }
}

@Composable
private fun ArticlesList(
    pagingItems: LazyPagingItems<NewsArticle>,
    onArticleClick: (article: NewsArticle) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues()
) {
    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = contentPadding,
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.id }
        ) { idx ->
            val article = pagingItems[idx] ?: return@items

            ListNewsCard(
                modifier = Modifier.fillMaxWidth(),
                title = article.title,
                source = article.source.id,
                sourceImageUrl = article.source.icon,
                newsImageUrl = article.headerImageUrl,
                date = article.publishDate.toString(),
                onClick = { onArticleClick(article) },
                showDivider = true,
            )
        }

        if (pagingItems.loadState.append is LoadState.Loading) item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.large),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        if (pagingItems.loadState.append is LoadState.Error) item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.large),
                contentAlignment = Alignment.Center,
            ) {
                PrimaryButton(
                    label = stringResource(R.string.feature_search_articles_list_load_more_btn_label),
                    icon = {
                        Icon(
                            painter = painterResource(CommonResources.drawable.common_resources_add),
                            contentDescription = null
                        )
                    },
                    onClick = {
                        pagingItems.retry()
                    }
                )
            }
        }
    }
}
