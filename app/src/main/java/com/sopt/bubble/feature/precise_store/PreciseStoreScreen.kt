package com.sopt.bubble.feature.precise_store

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.bubble.R
import com.sopt.bubble.feature.precise_store.component.PreciseStoreBottomBar
import com.sopt.bubble.feature.precise_store.component.PreciseStoreCheckBox
import com.sopt.bubble.feature.precise_store.component.PreciseStoreMoreButton
import com.sopt.bubble.feature.precise_store.component.PreciseStoreTicket
import com.sopt.bubble.feature.precise_store.component.PreciseStoreTopBar
import com.sopt.bubble.feature.precise_store.model.CHECK_BUTTON_NUM
import com.sopt.bubble.feature.precise_store.model.checkBoxList
import com.sopt.bubble.ui.theme.Body02
import com.sopt.bubble.ui.theme.Body03
import com.sopt.bubble.ui.theme.Gray300
import com.sopt.bubble.ui.theme.Gray400
import com.sopt.bubble.ui.theme.Gray500
import com.sopt.bubble.ui.theme.Gray700
import com.sopt.bubble.ui.theme.Gray800
import com.sopt.bubble.ui.theme.Headline04
import com.sopt.bubble.ui.theme.White

@Composable
fun PreciseStoreScreen(
    modifier: Modifier = Modifier,
    viewModel: PreciseStoreViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            PreciseStoreTopBar(
                onClickBackIcon = {},
                onClickCloseIcon = {}
            )
        },
        bottomBar = {
            PreciseStoreBottomBar(
                isChecked = uiState.isPurchasable,
                onClick = {}
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .background(Gray700)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = stringResource(id = R.string.app_name),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(PreciseStoreViewModel.PRECISE_STORE_TOP_IMAGE_RATIO)
            )


            PreciseStoreArtistDescription(
                uiState = uiState,
                modifier = Modifier.padding(
                    start = 20.dp, end = 20.dp, top = 16.dp
                )
            )

            PreciseMoreView(uiState = uiState)

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(color = Gray800)
            )

            PreciseStoreBubbleDescription(
                uiState = uiState,
                modifier = Modifier.padding(
                    start = 20.dp, end = 20.dp, top = 24.dp
                )
            )


            PreciseStoreCheckBoxes(
                onClickCheckBox = { viewModel.onClickCheckBox(it) },
                uiState = uiState,
                modifier = Modifier.padding(
                    start = 20.dp, end = 20.dp, top = 32.dp, bottom = 47.dp
                )
            )
        }
    }
}

@Composable
private fun PreciseStoreArtistDescription(
    modifier: Modifier = Modifier,
    uiState: PreciseStoreState
) {
    Column(
        modifier = modifier
    ) {
        /*아티스트 이름 텍스트*/
        Text(
            text = uiState.artistName,
            color = White,
            style = Headline04,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(1.dp)
                .background(color = Gray800)
        )

        /*아티스트 버블 소개 텍스트*/
        Text(
            text = uiState.bubbleDescription,
            color = Gray300,
            style = Body03,
            modifier = Modifier.padding(top = 20.dp)
        )

        /*아티스트 라인업 텍스트*/
        Text(
            text = stringResource(id = R.string.precise_store_artist_lineup),
            color = White,
            style = Body02,
            modifier = Modifier.padding(top = 18.dp),
        )

        Text(
            text = uiState.artistLineup,
            color = White,
            style = Body03,
            modifier = Modifier.padding(top = 6.dp)
        )

        /*아티스트 커밍순 텍스트*/
        if (uiState.artistComingSoon != null) {
            Text(
                text = stringResource(id = R.string.precise_store_artist_coming_soon),
                color = Gray500,
                style = Body02,
                modifier = Modifier.padding(top = 18.dp)
            )
            Text(
                text = uiState.artistComingSoon,
                color = Gray500,
                style = Body03,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }
}

@Composable
private fun PreciseMoreView(
    uiState: PreciseStoreState,
    moreIndex: Int = 2
) {
    var isMorePressed by remember{ mutableStateOf(false) }
    Column(
        modifier = Modifier
            .wrapContentSize()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
    ) {
        for (ticket in uiState.ticketList.subList(0, moreIndex)) {
            PreciseStoreTicket(
                title = ticket.number,
                price = ticket.price,
                originalPrice = ticket.originalPrice,
                modifier =
                if (uiState.ticketList.indexOf(ticket) != 0)
                    Modifier.padding(top = 14.dp)
                else Modifier.padding(top = 26.dp)
            )
        }
        if (isMorePressed) {
            for (ticket in uiState.ticketList.subList(
                moreIndex,
                uiState.ticketList.size
            )) {
                PreciseStoreTicket(
                    title = ticket.number,
                    price = ticket.price,
                    originalPrice = ticket.originalPrice,
                    modifier =
                    if (uiState.ticketList.indexOf(ticket) != 0)
                        Modifier.padding(top = 14.dp)
                    else Modifier.padding(top = 26.dp)
                )
            }
        }
    }

    if (uiState.ticketList.size > moreIndex) {
        PreciseStoreMoreButton(
            isMore = isMorePressed,
            onClick = { isMorePressed = !isMorePressed },
        )
    }
}

@Composable
private fun PreciseStoreBubbleDescription(
    modifier: Modifier = Modifier,
    uiState: PreciseStoreState
) {
    Column(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_precise_store_jyp_bubble),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(PreciseStoreViewModel.PRECISE_STORE_BANNER_IMAGE_RATIO),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.precise_store_bubble_introduction),
            color = White,
            style = Body02
        )

        Text(
            text = uiState.bubbleIntroduction,
            color = Gray400,
            style = Body03,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun PreciseStoreCheckBoxes(
    uiState: PreciseStoreState,
    onClickCheckBox: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {

        for(index in 0..<CHECK_BUTTON_NUM) {
            with(checkBoxList[index]) {
                this.onClickCheckBox = {onClickCheckBox(index)}
                this.isChecked = uiState.isCheckedList[index]

                PreciseStoreCheckBox(
                    checkBoxContent = checkBoxList[index]
                )

                if(index < CHECK_BUTTON_NUM-1) {
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PrecisePreview() {
    PreciseStoreScreen()
}