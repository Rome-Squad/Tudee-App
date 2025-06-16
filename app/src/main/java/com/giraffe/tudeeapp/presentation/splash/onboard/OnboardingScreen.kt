package com.giraffe.tudeeapp.presentation.splash.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.button_type.TudeeFabButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.presentation.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingScreen(
    viewModel: SplashViewModel = koinViewModel(), onFinish: () -> Unit
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
            .background(
                color = Theme.color.overlay
            )
    ) {
        Image(
            painter = painterResource(id = Theme.resources.bacgroundImage),
            contentDescription = stringResource(R.string.splash_background),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )

        if (pagerState.currentPage < pages.lastIndex) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, top = 56.dp)
                    .align(Alignment.TopStart)
                    .clickable {
                        scope.launch {
                            viewModel.setOnboardingShown(true)
                            onFinish()
                        }
                    },
                text = stringResource(R.string.skip),
                style = Theme.textStyle.label.large,
                color = Theme.color.primary
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                HorizontalPager(
                    state = pagerState, modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.55f)
                ) { page ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = pages[page].imageRes),
                            contentDescription = null,
                            modifier = Modifier.padding(top = 32.dp)
                        )

                        Text(
                            modifier = Modifier
                                .padding(top = 56.dp)
                                .height(44.dp),
                            text = pages[page].title,
                            style = Theme.textStyle.title.medium,
                            textAlign = TextAlign.Center,
                            color = Theme.color.title,
                            maxLines = 2
                        )

                        Text(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .height(60.dp),
                            text = pages[page].description,
                            style = Theme.textStyle.body.medium,
                            textAlign = TextAlign.Center,
                            color = Theme.color.body,
                            maxLines = 3,
                            minLines = 3
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .height(192.dp)
                        .background(
                            color = Theme.color.onPrimaryCard, shape = RoundedCornerShape(32.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Theme.color.onPrimaryStroke,
                            shape = RoundedCornerShape(32.dp)
                        )
                )

                TudeeFabButton(
                    modifier = Modifier
                        .offset(y = 35.dp)
                        .align(Alignment.BottomCenter),
                    isLoading = false,
                    isDisable = false,
                    icon = painterResource(R.drawable.arrow_right_double),
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage < pages.lastIndex) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            } else {
                                viewModel.setOnboardingShown(true)
                                onFinish()
                            }
                        }
                    })
            }


            DotsIndicator(
                totalDots = pages.size,
                selectedIndex = pagerState.currentPage,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 59.dp, bottom = 24.dp)
            )
        }
    }
}

data class OnboardingData(
    val imageRes: Int, val title: String, val description: String
)

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    dotSpacing: Dp = 10.dp,
    dotHeight: Dp = 5.dp
) {
    val selectedColor = Theme.color.primary
    val unSelectedColor = Theme.color.onPrimaryStroke

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(dotSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(dotHeight)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(if (index == selectedIndex) selectedColor else unSelectedColor)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(
        onFinish = {})
}