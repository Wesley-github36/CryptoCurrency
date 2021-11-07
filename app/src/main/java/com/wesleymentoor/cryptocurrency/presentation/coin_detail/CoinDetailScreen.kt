package com.wesleymentoor.cryptocurrency.presentation.coin_detail

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wesleymentoor.cryptocurrency.common.Resource
import com.wesleymentoor.cryptocurrency.common.Status
import com.wesleymentoor.cryptocurrency.domain.model.CoinDetail
import com.wesleymentoor.cryptocurrency.presentation.coin_detail.components.TagRow
import com.wesleymentoor.cryptocurrency.presentation.coin_detail.components.TeamListItem

@Composable
fun CoinDetailScreen(
    viewModel: CoinDetailViewModel = hiltViewModel()
) {
    val coinDetail: Resource<CoinDetail> by viewModel.coinDetail.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (coinDetail.status) {
            Status.LOADING -> {
                CircularProgressIndicator(color = Color.Green, modifier = Modifier.align(Alignment.Center))
            }
            Status.SUCCESS -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Text(
                                    text = "${coinDetail.data!!.rank}. ${coinDetail.data!!.name} (${coinDetail.data!!.symbol})",
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.weight(8f)
                                )
                                Text(
                                    text = if (coinDetail.data!!.isActive) "active" else "inactive",
                                    color = if (coinDetail.data!!.isActive) Color.Green else Color.Red,
                                    fontStyle = FontStyle.Italic,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier
                                        .align(CenterVertically)
                                        .weight(2f)

                                )
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = coinDetail.data!!.description,
                                style = MaterialTheme.typography.body2
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Tags",
                                style = MaterialTheme.typography.body1
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            TagRow(tags = coinDetail.data!!.tags, modifier = Modifier)
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Team Members",
                                style = MaterialTheme.typography.body1
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                        items(coinDetail.data!!.team) { teamMemeber ->
                            TeamListItem(
                                teamMember = teamMemeber,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            )
                            Divider()
                        }
                    }
                }
            }
            Status.ERROR -> {
                Text(
                    text = coinDetail.msg!!,
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