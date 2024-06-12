package org.dreamerslab.newslayer.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.dreamerslab.newslayer.ui.components.SelectableChip
import org.dreamerslab.newslayer.ui.theme.spacing
import org.dreamerslab.newslayer.common.resources.R as CommonResources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Configure")
                },
                navigationIcon = {
                    IconButton(onNavigateUp) {
                        Icon(
                            painter = painterResource(CommonResources.drawable.arrow_back),
                            contentDescription = "Navigate Up"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            InfoBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.large)
            )

            CategorySelectionSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.large)
            )
        }
    }
}

@Composable
private fun InfoBanner(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.small,
                Alignment.CenterHorizontally
            ),
        ) {
            CompositionLocalProvider(
                LocalContentColor provides MaterialTheme.colorScheme.onSurface
            ) {
                Icon(
                    painter = painterResource(CommonResources.drawable.info),
                    contentDescription = null,
                )

                Text(
                    text = "You can always change these settings later.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Immutable
private data class Category(
    val name: String,
    val selected: Boolean
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategorySelectionSection(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Select Categories",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))

            Text(
                text = "Choose up to 5 categories you’re interested in",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(Modifier.height(MaterialTheme.spacing.large))

            val categories = remember {
                mutableStateListOf(
                    Category("Business", false),
                    Category("Technology", false),
                    Category("Sports", false),
                    Category("Tourism", false),
                    Category("Science", false),
                    Category("Politics", false),
                    Category("Health", false),
                    Category("Education", false),
                    Category("Food", false),
                    Category("LifeStyle", false),
                    Category("Entertainment", false),
                    Category("World", false)
                )
            }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            ) {
                categories.forEachIndexed { idx, category ->
                    SelectableChip(
                        label = category.name,
                        selected = category.selected,
                        onClick = {
                            categories[idx] = category.copy(selected = !category.selected)
                        },
                        selectedIcon = {
                            Icon(
                                painter = painterResource(CommonResources.drawable.check),
                                contentDescription = null,
                            )
                        }
                    )
                }
            }
        }
    }
}
