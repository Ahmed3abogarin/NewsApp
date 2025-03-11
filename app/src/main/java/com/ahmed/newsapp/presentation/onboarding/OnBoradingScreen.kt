package com.ahmed.newsapp.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ahmed.newsapp.presentation.common.NewsBackButton
import com.ahmed.newsapp.presentation.common.NewsButton
import com.ahmed.newsapp.presentation.onboarding.components.OnBoardingPage
import com.ahmed.newsapp.presentation.onboarding.components.PageIndicator
import com.ahmed.newsapp.util.Dimens.MediumPadding2
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    onEvent: (OnBoardingEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val pagerState = rememberPagerState(initialPage = 0) {
            pages.size
        }

        val buttonState = remember {
            derivedStateOf {
                when (pagerState.currentPage) {
                    0 -> listOf("", "Next")
                    1 -> listOf("Back", "Next")
                    2 -> listOf("", "Get Stated")
                    else -> listOf("", "")
                }
            }
        }
        HorizontalPager(state = pagerState) { index ->
            OnBoardingPage(page = pages[index], modifier = Modifier.padding(start = 16.dp, end = 16.dp))
        }
        // To put the Indicators and buttons at the bottom of the screen we can use "Spacer" and weight "1f"
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding2)
                .padding(start = 16.dp, end = 16.dp)
                .navigationBarsPadding() // This will calculate the bottom navigation space and then adding it at the bottom of the Row
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PageIndicator(
                modifier = Modifier.width(52.dp),
                pageSize = pages.size,
                selectedPage = pagerState.currentPage
            )


            Row(verticalAlignment = Alignment.CenterVertically) {
                val coroutine = rememberCoroutineScope()
                if (buttonState.value[0].isNotEmpty()) {
                    NewsBackButton(
                        text = buttonState.value[0],
                        onClick = {
                            coroutine.launch {
                                pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                            }
                        })
                }

                NewsButton(
                    text = buttonState.value[1],
                    onClick = {
                        coroutine.launch {
                            if (pagerState.currentPage == 2) {
                                onEvent(OnBoardingEvent.SaveAppEntry)
                            } else {
                                pagerState.animateScrollToPage(
                                    page = pagerState.currentPage + 1
                                )
                            }
                        }
                    })
            }
        }

        Spacer(modifier = Modifier.weight(0.5f))


        // One thing is remaining is changing the system top bar: By add this to MainActivity :WindowCompat.setDecorFitsSystemWindows(window,false)
    }
}