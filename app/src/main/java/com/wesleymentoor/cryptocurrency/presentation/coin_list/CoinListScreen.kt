package com.wesleymentoor.cryptocurrency.presentation.coin_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wesleymentoor.cryptocurrency.common.Resource
import com.wesleymentoor.cryptocurrency.common.Status
import com.wesleymentoor.cryptocurrency.domain.model.Coin
import com.wesleymentoor.cryptocurrency.presentation.Screen
import com.wesleymentoor.cryptocurrency.presentation.coin_list.components.CoinListItem

@Composable
fun CoinListScreen(
    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val coinList: Resource<List<Coin>> by viewModel.coinList.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (coinList.status) {
            Status.LOADING -> {
                CircularProgressIndicator(color = Color.Green, modifier = Modifier.align(Alignment.Center))
            }
            Status.SUCCESS -> {
                LazyColumn {
                    items(coinList.data!!) { coin ->
                        CoinListItem(coin = coin) {
                            navController.navigate(Screen.CoinDetailsScreen.route + "/${coin.id}") {
                                launchSingleTop = true
                            }
                        }
                    }
                }
            }
            Status.ERROR -> {
                Text(
                    text = coinList.msg!!,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}