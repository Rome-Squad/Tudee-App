package com.giraffe.tudeeapp.presentation.screen.onboard

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.button_type.FabButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.presentation.app.AppViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingScreen(
    viewModel: AppViewModel = koinViewModel(),
    onFinish: () -> Unit = {}
) {
    OnboardingContent(viewModel::setFirsTimeStatus, onFinish)
}

@Composable
private fun OnboardingContent(
    setFirstTimeStatus: () -> Unit = {},
    onFinish: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val pages = listOf(
        OnboardingData(
            imageRes = R.drawable.image_container_1,
            title = stringResource(R.string.overwhelmed_with_tasks),
            description = stringResource(R.string.let_s_bring_some_order_to_the_chaos_tudee_is_here_to_help_you_sort_plan_and_breathe_easier)
        ), OnboardingData(
            imageRes = R.drawable.image_container_2,
            title = stringResource(R.string.uh_oh_procrastinating_again),
            description = stringResource(R.string.tudee_not_mad_just_a_little_disappointed)
        ), OnboardingData(
            imageRes = R.drawable.image_container_3,
            title = stringResource(R.string.let_s_complete_tasks_and_celebrate_together),
            description = stringResource(R.string.tudee_will_celebrate_you_on_every_win)
        )
    )
    val pagerState = rememberPagerState(pageCount = { pages.size })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(Theme.color.surface)
            .background(Theme.color.overlay)
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = Theme.drawables.bacgroundImage),
            contentDescription = stringResource(R.string.splash_background),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (pagerState.currentPage < pages.lastIndex) {
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Start)
                        .clickable {
                            scope.launch {
                                setFirstTimeStatus()
                                onFinish()
                            }
                        },
                    text = stringResource(R.string.skip),
                    style = Theme.textStyle.label.large,
                    color = Theme.color.primary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            HorizontalPager(
                modifier = Modifier.fillMaxWidth(),
                state = pagerState,
            ) { page ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp),
                        painter = painterResource(id = pages[page].imageRes),
                        contentDescription = pages[page].title,
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .background(
                                color = Theme.color.onPrimaryCard,
                                shape = RoundedCornerShape(32.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Theme.color.onPrimaryStroke,
                                shape = RoundedCornerShape(32.dp)
                            )
                            .padding(top = 24.dp, bottom = 48.dp)
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = pages[page].title,
                            style = Theme.textStyle.title.medium,
                            textAlign = TextAlign.Center,
                            color = Theme.color.title,
                            maxLines = 2
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = pages[page].description,
                            style = Theme.textStyle.body.medium,
                            textAlign = TextAlign.Center,
                            color = Theme.color.body,
                            maxLines = 3,
                        )
                    }
                }
            }
            FabButton(
                modifier = Modifier.offset(y = (-32).dp),
                icon = painterResource(R.drawable.arrow_right_double),
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage < pages.lastIndex) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            setFirstTimeStatus()
                            onFinish()
                        }
                    }
                }
            )
            DotsIndicator(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 24.dp),
                totalDots = pages.size,
                selectedIndex = pagerState.currentPage,
            )
        }
    }
}

@Composable
private fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    dotSpacing: Dp = 10.dp,
    dotHeight: Dp = 5.dp
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dotSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalDots) { index ->
            val color by animateColorAsState(if (index == selectedIndex) Theme.color.primary else Theme.color.onPrimaryStroke)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(dotHeight)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(color)
            )
        }
    }
}

data class OnboardingData(
    val imageRes: Int, val title: String, val description: String
)

@Preview(device = Devices.TABLET, showSystemUi = true, locale = "ar")
@Composable
private fun Preview() {
    TudeeTheme {
        OnboardingContent()
    }
}