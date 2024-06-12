package org.dreamerslab.newslayer.ui.onboarding

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import org.dreamerslab.newslayer.R
import org.dreamerslab.newslayer.ui.components.PrimaryButton
import org.dreamerslab.newslayer.ui.theme.spacing

@Composable
fun WelcomeScreen(
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    UpdateSystemBarColorsSideEffect()

    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter,
        ) {
            BottomSheetContent(
                paddingValues = paddingValues,
                onNextClick = onNextClick,
            )
        }
    }
}

@Composable
private fun BottomSheetContent(
    paddingValues: PaddingValues,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = Modifier
            .widthIn(max = 640.dp)
            .fillMaxWidth()
            .then(modifier),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(
            topStart = 32.dp,
            topEnd = 32.dp,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.spacing.extraLarge)
                .padding(bottom = paddingValues.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = stringResource(R.string.welcome_screen_message),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            PrimaryButton(
                label = stringResource(R.string.welcome_screen_get_started_button_label),
                onClick = onNextClick,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun UpdateSystemBarColorsSideEffect() {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }
}
