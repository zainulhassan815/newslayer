package org.dreamerslab.newslayer.ui.utils

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    group = "Dark Theme",
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    group = "Light Theme",
)
annotation class PreviewMultiTheme
