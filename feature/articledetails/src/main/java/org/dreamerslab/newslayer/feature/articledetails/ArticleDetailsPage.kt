package org.dreamerslab.newslayer.feature.articledetails

import org.dreamerslab.newslayer.common.resources.R as CommonResources
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.dreamerslab.newslayer.core.model.NewsArticle
import org.dreamerslab.newslayer.ui.components.ErrorMessage
import org.dreamerslab.newslayer.ui.components.FeaturedNewsCard
import org.dreamerslab.newslayer.ui.components.PrimaryButton
import org.dreamerslab.newslayer.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsPage(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArticleDetailsViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onNavigateUp) {
                        Icon(
                            painter = painterResource(CommonResources.drawable.common_resources_arrow_back),
                            contentDescription = stringResource(R.string.feature_articledetails_cd_navigate_up_icon),
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        val uiState = viewModel.state.collectAsStateWithLifecycle()
        val context = LocalContext.current

        when (val state = uiState.value) {
            ArticleDetailsState.Loading -> ArticleLoading()

            ArticleDetailsState.InvalidArticleId -> ErrorMessage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(MaterialTheme.spacing.large),
                title = stringResource(R.string.feature_articledetails_invalid_id_error_title),
                subtitle = stringResource(R.string.feature_articledetails_invalid_id_error_msg),
                icon = {
                    Icon(
                        painter = painterResource(CommonResources.drawable.common_resources_report),
                        contentDescription = null
                    )
                }
            )

            ArticleDetailsState.Error -> ErrorMessage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(MaterialTheme.spacing.large),
                title = stringResource(R.string.feature_articledetails_error_title),
                subtitle = stringResource(R.string.feature_articledetails_error_msg),
                icon = {
                    Icon(
                        painter = painterResource(CommonResources.drawable.common_resources_report),
                        contentDescription = null
                    )
                },
                action = {
                    PrimaryButton(
                        label = stringResource(R.string.feature_articledetails_retry_btn_label),
                        onClick = {
                            // TODO: Reload article details
                        }
                    )
                }
            )

            is ArticleDetailsState.Success -> ArticleDetails(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = MaterialTheme.spacing.large),
                article = state.article,
                onReadArticleClick = {
                    viewModel.launchUrl(context, state.article.link)
                }
            )
        }
    }
}

@Composable
private fun ArticleLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ArticleDetails(
    article: NewsArticle,
    onReadArticleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        FeaturedNewsCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.spacing.medium),
            title = article.title,
            source = article.source.id,
            sourceImageUrl = article.source.icon,
            newsImageUrl = article.headerImageUrl,
            date = article.publishDate.toString(),
            onClick = {}
        )

        article.description
            .takeIf { it.isNotEmpty() }
            ?.let { description ->
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            }

        PrimaryButton(
            label = stringResource(R.string.feature_articledetails_read_full_article_btn_label),
            onClick = onReadArticleClick
        )
    }
}
