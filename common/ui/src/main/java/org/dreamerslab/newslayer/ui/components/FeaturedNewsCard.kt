package org.dreamerslab.newslayer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.dreamerslab.newslayer.ui.theme.NewsLayerTheme
import org.dreamerslab.newslayer.ui.theme.spacing
import org.dreamerslab.newslayer.ui.utils.PreviewMultiTheme

@Composable
fun FeaturedNewsCard(
    title: String,
    source: String,
    date: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    newsImageUrl: String? = null,
    sourceImageUrl: String? = null,
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .then(modifier),
    ) {
        FeaturedImage(
            imageUrl = newsImageUrl,
            contentDescription = title,
        )

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
}

@Composable
private fun FeaturedImage(
    imageUrl: String?,
    contentDescription: String? = null,
) {
    if (imageUrl != null) {
        if (LocalView.current.isInEditMode) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
            )
        } else {
            AsyncImage(
                model = imageUrl,
                contentDescription = contentDescription,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
            )
        }

        Spacer(Modifier.height(MaterialTheme.spacing.large))
    }
}

@PreviewMultiTheme
@Composable
private fun FeaturedNewsCardPreview() {
    NewsLayerTheme {
        FeaturedNewsCard(
            title = "Apollo 8 astronaut William Anders, who took iconic Earthrise photo, killed in plane crash",
            source = "centralmine",
            date = "08 Jun 2024",
            newsImageUrl = "",
            sourceImageUrl = "",
            onClick = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .width(412.dp)
                .padding(16.dp),
        )
    }
}

@PreviewMultiTheme
@Composable
private fun FeaturedNewsCardWithoutImagePreview() {
    NewsLayerTheme {
        FeaturedNewsCard(
            title = "Apollo 8 astronaut William Anders, who took iconic Earthrise photo, killed in plane crash",
            source = "centralmine",
            date = "08 Jun 2024",
            sourceImageUrl = "",
            onClick = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .width(412.dp)
                .padding(16.dp),
        )
    }
}
