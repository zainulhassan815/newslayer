package org.dreamerslab.newslayer.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.dreamerslab.newslayer.ui.theme.NewsLayerTheme
import org.dreamerslab.newslayer.ui.theme.spacing
import org.dreamerslab.newslayer.ui.utils.PreviewMultiTheme

@Composable
fun SelectableChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    selectedIcon: (@Composable () -> Unit)? = null,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        label = "Chip Background Color",
    )

    Surface(
        modifier = Modifier
            .animateContentSize()
            .then(modifier),
        onClick = onClick,
        color = backgroundColor,
        contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        tonalElevation = if (selected) 0.dp else 5.dp,
        shape = CircleShape,
    ) {
        Box(
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.spacing.large,
                    vertical = MaterialTheme.spacing.small,
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (icon != null || selectedIcon != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                ) {
                    ChipIcon(
                        selected = selected,
                        icon = icon,
                        selectedIcon = selectedIcon,
                    )

                    ChipText(text = label)
                }
            } else {
                ChipText(text = label)
            }
        }
    }
}

@Composable
private fun ChipIcon(
    selected: Boolean,
    icon: (@Composable () -> Unit)?,
    selectedIcon: (@Composable () -> Unit)?,
) {
    val sizeModifier = Modifier.size(18.dp)
    when {
        icon != null && selectedIcon != null -> {
            AnimatedContent(
                targetState = selected,
                label = "Chip Icon",
                transitionSpec = {
                    (scaleIn() togetherWith scaleOut())
                },
                modifier = sizeModifier,
            ) { isSelected ->
                if (isSelected) {
                    selectedIcon()
                } else {
                    icon()
                }
            }
        }

        selected && selectedIcon != null -> {
            Box(sizeModifier) { selectedIcon() }
        }

        icon != null -> {
            Box(sizeModifier) { icon() }
        }
    }
}

@Composable
private fun ChipText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier,
    )
}

@PreviewMultiTheme
@Composable
private fun SelectableChipWithIconPreview() {
    NewsLayerTheme {
        Surface(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        ) {
            var selected by remember { mutableStateOf(false) }

            SelectableChip(
                label = "Selectable Chip",
                selected = selected,
                onClick = { selected = !selected },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}

@PreviewMultiTheme
@Composable
private fun SelectableChipWithoutIconPreview() {
    NewsLayerTheme {
        Surface(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        ) {
            var selected by remember { mutableStateOf(false) }

            SelectableChip(
                label = "Selectable Chip",
                selected = selected,
                onClick = { selected = !selected },
            )
        }
    }
}
