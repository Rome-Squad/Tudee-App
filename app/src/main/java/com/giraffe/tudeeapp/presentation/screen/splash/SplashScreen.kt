package com.giraffe.tudeeapp.presentation.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.presentation.app.AppViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    viewModel: AppViewModel = koinViewModel(),
    navigateToHome: () -> Unit,
    navigateToOnboarding: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state.isFirstTime) {
        state.isFirstTime?.let {
            delay(3000)
            if (it) navigateToOnboarding() else navigateToHome()
        }
    }
    SplashContent()
}

@Composable
private fun SplashContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
            .background(Theme.color.overlay)
    ) {
        Image(
            painter = painterResource(id = Theme.drawables.bacgroundImage),
            contentDescription = stringResource(R.string.splash_background),
            modifier = Modifier
                .fillMaxWidth(), contentScale = ContentScale.FillWidth
        )

        Image(
            painter = painterResource(id = Theme.drawables.logoImageResId),
            contentDescription = stringResource(R.string.splash_logo),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    TudeeTheme {
        SplashContent()
    }
}
