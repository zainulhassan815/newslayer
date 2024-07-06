package org.dreamerslab.newslayer.feature.home

import org.dreamerslab.newslayer.common.resources.R as CommonResources
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
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
            title = "Error Occurred",
            subtitle = "We are having trouble connecting the server. Please make sure you have an active network connection and try again.",
            icon = {
                Icon(
                    painter = painterResource(CommonResources.drawable.common_resources_report),
                    contentDescription = null
                )
            },
            action = {
                PrimaryButton(
                    label = "Try Again",
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

        else -> ArticlesList(
            modifier = modifier,
            pagingItems = pagingItems,
            onArticleClick = {}
        )
    }
}

@Composable
fun ArticlesList(
    pagingItems: LazyPagingItems<NewsArticle>,
    onArticleClick: (article: NewsArticle) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
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
                    label = "Load More Articles",
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
