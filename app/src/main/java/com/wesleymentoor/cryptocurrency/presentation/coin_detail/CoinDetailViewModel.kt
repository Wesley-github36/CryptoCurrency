package com.wesleymentoor.cryptocurrency.presentation.coin_detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wesleymentoor.cryptocurrency.common.Constants
import com.wesleymentoor.cryptocurrency.common.Resource
import com.wesleymentoor.cryptocurrency.common.dispatcher.Dispatcher
import com.wesleymentoor.cryptocurrency.data.remote.dto.asDomainModel
import com.wesleymentoor.cryptocurrency.domain.model.CoinDetail
import com.wesleymentoor.cryptocurrency.domain.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val repository: CoinRepository,
    private val dispatcher: Dispatcher,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _coinDetail = MutableStateFlow<Resource<CoinDetail>>(Resource.loading(null))
    val coinDetail = _coinDetail as StateFlow<Resource<CoinDetail>>

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let {
            getCoin(it)
        }

        Log.i("CoinDetailViewModel", "$coinDetail")
    }

    /**
     * Loading data from the repo async on the background thread and updating the StateFlow
     * ether with data received in the case of success or an error message.
     */
    private fun getCoin(coinId: String) = viewModelScope.launch(dispatcher.ioDispatcher()) {
        repository.getCoinById(coinId)
            .flowOn(dispatcher.ioDispatcher())
            .catch { error ->

                when (error) {
                    is HttpException ->
                        _coinDetail.value = Resource.error<CoinDetail>(error.localizedMessage?: "Something went wrong")
                    is IOException ->
                        _coinDetail.value = Resource.error<CoinDetail>("Could not connection to internet. Check connection")
                    else ->
                        _coinDetail.value = Resource.error<CoinDetail>(error.localizedMessage?: "An unknown error occurred")
                }
            }
            .collect { data ->
                _coinDetail.value = Resource.success(data.asDomainModel())
            }
    }
}