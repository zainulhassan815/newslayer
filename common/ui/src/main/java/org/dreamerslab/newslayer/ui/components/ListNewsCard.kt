package org.dreamerslab.newslayer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.dreamerslab.newslayer.ui.theme.NewsLayerTheme
import org.dreamerslab.newslayer.ui.theme.spacing
import org.dreamerslab.newslayer.ui.utils.PreviewMultiTheme

@Composable
fun ListNewsCard(
    title: String,
    source: String,
    date: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    newsImageUrl: String? = null,
    sourceImageUrl: String? = null,
    showDivider: Boolean = false,
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .background(MaterialTheme.colorScheme.surface)
                .padding(MaterialTheme.spacing.large),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f),
            ) {
                SourceInfoRow(
                    source = source,
                    sourceImageUrl = sourceImageUrl,
                )

                Spacer(Modifier.height(MaterialTheme.spacing.medium))

                Text(
                    text = title,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(Modifier.height(MaterialTheme.spacing.medium))

                Text(
                    text = date,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            NewsImage(
                imageUrl = newsImageUrl,
                contentDescription = title,
            )
        }

        if (showDivider) HorizontalDivider(
            color = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
private fun NewsImage(
    imageUrl: String?,
    contentDescription: String?,
) {
    if (imageUrl != null) {
        if (LocalView.current.isInEditMode) {
            Spacer(
                modifier = Modifier
                    .size(60.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
            )
        } else {
            AsyncImage(
                model = imageUrl,
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(60.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@PreviewMultiTheme
@Composable
private fun ListNewsCardPreview() {
    NewsLayerTheme {
        ListNewsCard(
            title = "Apollo 8 astronaut William Anders, who took iconic Earthrise photo, killed in plane crash",
            source = "centralmine",
            date = "08 Jun 2024",
            newsImageUrl = "",
            sourceImageUrl = "",
            onClick = {},
            modifier = Modifier.width(412.dp),
        )
    }
}

@PreviewMultiTheme
@Composable
private fun ListNewsCardWithoutImagePreview() {
    NewsLayerTheme {
        ListNewsCard(
            title = "Apollo 8 astronaut William Anders, who took iconic Earthrise photo, killed in plane crash",
            source = "centralmine",
            date = "08 Jun 2024",
            sourceImageUrl = "",
            onClick = {},
            modifier = Modifier.width(412.dp),
        )
    }
}
