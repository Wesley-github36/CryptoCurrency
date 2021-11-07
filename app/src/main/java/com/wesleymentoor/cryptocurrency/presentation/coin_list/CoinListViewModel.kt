package com.wesleymentoor.cryptocurrency.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wesleymentoor.cryptocurrency.common.Resource
import com.wesleymentoor.cryptocurrency.common.dispatcher.Dispatcher
import com.wesleymentoor.cryptocurrency.data.remote.dto.asDomainModel
import com.wesleymentoor.cryptocurrency.domain.model.Coin
import com.wesleymentoor.cryptocurrency.domain.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val repository: CoinRepository,
    private val dispatcher: Dispatcher
): ViewModel() {
    private val _coinList = MutableStateFlow<Resource<List<Coin>>>(Resource.loading(null))
    val coinList = _coinList as StateFlow<Resource<List<Coin>>>

    init {
        getCoinList()
    }

    /**
     * Loading a list of coins from the repo async on the background thread and updating the StateFlow
     * ether with data received in the case of success or an error message.
     */
    private fun getCoinList() = viewModelScope.launch(dispatcher.ioDispatcher()) {
        repository.getCoins()
            .flowOn(dispatcher.ioDispatcher())
            .catch { error ->

                when (error) {
                    is HttpException ->
                        _coinList.value = Resource.error<List<Coin>>(error.localizedMessage?: "Something went wrong")
                    is IOException ->
                        _coinList.value = Resource.error<List<Coin>>("Could not connection to internet. Check connection")
                    else ->
                        _coinList.value = Resource.error<List<Coin>>("An unknown error occurred")
                }
            }
            .collect { data ->
                _coinList.value = Resource.success(data.asDomainModel())
            }
    }
}