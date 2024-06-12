package org.dreamerslab.newslayer.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.dreamerslab.newslayer.ui.theme.NewsLayerTheme
import org.dreamerslab.newslayer.ui.theme.spacing
import org.dreamerslab.newslayer.ui.utils.MultiThemePreview

@Composable
fun PrimaryButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    Button(
        modifier = modifier,
        enabled = enabled && !isLoading,
        onClick = onClick,
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.spacing.extraLarge,
            vertical = MaterialTheme.spacing.medium,
        ),
    ) {
        AnimatedContent(isLoading, label = "Primary Button Content Visibility") { loading ->
            if (loading) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(24.dp),
                    strokeCap = StrokeCap.Round,
                )
            } else {
                if (icon != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                    ) {
                        icon()
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                } else {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}

@MultiThemePreview
@Composable
private fun PrimaryButtonPreview() {
    NewsLayerTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        ) {
            PrimaryButton(
                label = "Get Started",
                onClick = {},
                icon = {
                    Icon(
                        painter = painterResource(org.dreamerslab.newslayer.common.resources.R.drawable.check),
                        contentDescription = ""
                    )
                },
            )
        }
    }
}

@MultiThemePreview
@Composable
private fun PrimaryButtonWithoutIconPreview() {
    NewsLayerTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        ) {
            PrimaryButton(
                label = "Get Started",
                onClick = {},
            )
        }
    }
}

@MultiThemePreview
@Composable
private fun PrimaryButtonLoadingPreview() {
    NewsLayerTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        ) {
            PrimaryButton(
                label = "Get Started",
                onClick = {},
                icon = {
                    Icon(
                        painter = painterResource(org.dreamerslab.newslayer.common.resources.R.drawable.check),
                        contentDescription = ""
                    )
                },
                isLoading = true,
            )
        }
    }
}
