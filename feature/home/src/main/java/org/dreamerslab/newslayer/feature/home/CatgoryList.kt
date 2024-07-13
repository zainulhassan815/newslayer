package org.dreamerslab.newslayer.feature.home

import org.dreamerslab.newslayer.common.resources.R as CommonResources
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.launch
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import org.dreamerslab.newslayer.core.model.NewsArticle
import org.dreamerslab.newslayer.ui.components.ErrorMessage
import org.dreamerslab.newslayer.ui.components.ListNewsCard
import org.dreamerslab.newslayer.ui.components.PrimaryButton
import org.dreamerslab.newslayer.ui.theme.spacing

private val dateFormat = DateTimeComponents.Format {
    dayOfMonth()
    char(' ')
    monthName(MonthNames.ENGLISH_ABBREVIATED)
    char(' ')
    year()
}

@Composable
fun CategoryList(
    viewModel: CategoryListViewModel,
    onArticleClick: (article: NewsArticle) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagingItems = viewModel.pagingData.collectAsLazyPagingItems()

    val loading = pagingItems.loadState.refresh is LoadState.Loading
    val hasError = pagingItems.loadState.refresh is LoadState.Error

    when {
        hasError -> ErrorMessage(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.large),
            title = stringResource(R.string.feature_home_category_list_error_title),
            subtitle = stringResource(R.string.feature_home_category_list_error_message),
            icon = {
                Icon(
                    painter = painterResource(CommonResources.drawable.common_resources_report),
                    contentDescription = null
                )
            },
            action = {
                PrimaryButton(
                    label = stringResource(R.string.feature_home_category_list_try_again_btn_label),
                    onClick = {
                        pagingItems.refresh()
                    }
                )
            }
        )

        loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }

        else -> ArticlesListScaffold(
            modifier = modifier,
            pagingItems = pagingItems,
            onArticleClick = onArticleClick
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
fun ArticlesList(
    pagingItems: LazyPagingItems<NewsArticle>,
    onArticleClick: (article: NewsArticle) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues()
) {
    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = contentPadding
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.id }
        ) { idx ->
            val article = pagingItems[idx] ?: return@items
            val date = remember { article.publishDate.format(dateFormat) }

            ListNewsCard(
                modifier = Modifier.fillMaxWidth(),
                title = article.title,
                source = article.source.id,
                sourceImageUrl = article.source.icon,
                newsImageUrl = article.headerImageUrl,
                date = date,
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
                    label = stringResource(R.string.feature_home_category_list_load_more_articles_btn_label),
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
