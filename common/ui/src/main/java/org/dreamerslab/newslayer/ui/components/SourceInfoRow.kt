package org.dreamerslab.newslayer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.dreamerslab.newslayer.ui.theme.NewsLayerTheme
import org.dreamerslab.newslayer.ui.theme.spacing
import org.dreamerslab.newslayer.ui.utils.PreviewMultiTheme

@Composable
fun SourceInfoRow(
    source: String,
    sourceImageUrl: String?,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
    ) {
        if (sourceImageUrl == null) {
            Text(
                text = source,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (LocalView.current.isInEditMode) {
                    Spacer(
                        modifier = Modifier
                            .size(18.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                    )
                } else {
                    AsyncImage(
                        model = sourceImageUrl,
                        contentDescription = source,
                        modifier = Modifier
                            .size(18.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                    )
                }

                Text(
                    text = source,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@PreviewMultiTheme
@Composable
private fun SourceInfoPreview() {
    NewsLayerTheme {
        Surface {
            SourceInfoRow(
                source = "The New York Times",
                sourceImageUrl = "",
            )
        }
    }
}

@PreviewMultiTheme
@Composable
private fun SourceInfoWithoutIconPreview() {
    NewsLayerTheme {
        Surface {
            SourceInfoRow(
                source = "The New York Times",
                sourceImageUrl = null,
            )
        }
    }
}
