package org.dreamerslab.newslayer.feature.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import org.dreamerslab.newslayer.core.model.NewsArticle
import org.dreamerslab.newslayer.ui.components.ListNewsCard

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
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (state) {
        CategoryListState.Error -> Text(text = "Error")
        CategoryListState.Loading -> CircularProgressIndicator()
        is CategoryListState.Success -> ArticlesList(
            modifier = modifier,
            articles = (state as CategoryListState.Success).articles,
            onArticleClick = {}
        )
    }
}

@Composable
fun ArticlesList(
    articles: List<NewsArticle>,
    onArticleClick: (article: NewsArticle) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = articles,
            key = { it.id }
        ) { article ->
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
    }
}
