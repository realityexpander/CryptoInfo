package com.realityexpander.cryptoapp.presentation.coin_info

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realityexpander.cryptoapp.common.Constants.PARAM_COIN_ID
import com.realityexpander.cryptoapp.common.Resource
import com.realityexpander.cryptoapp.data.remote.dto.CoinOHLCVItemDTO
import com.realityexpander.cryptoapp.data.remote.dto.CoinOHLCVItemsDTO
import com.realityexpander.cryptoapp.domain.use_case.get_coin.GetCoinInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL
import javax.inject.Inject

// ViewModel is necessary to keep the state of the view between configuration changes

@HiltViewModel
class CoinInfoViewModel @Inject constructor(
    private val getCoinInfoUseCase: GetCoinInfoUseCase, // this has the repository injected
    savedStateHandle: SavedStateHandle  // gets nav params (PARAM_COIN_ID = coinId)
) : ViewModel() {

    var state = mutableStateOf(CoinInfoState(isLoading = true))
        private set

    init {
        savedStateHandle.get<String>(PARAM_COIN_ID)?.also { coinId ->
            getCoinInfo(coinId)
        }
    }

    private fun getCoinInfo(coinId: String) {
        getCoinInfoUseCase(coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        coinInfo = result.data
                    )
                    getCoinPrice(coinId) // get the price *after* we have a valid CoinInfo object
                }
                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = result.errorMessage ?: "Unknown Error"
                    )
                }
                is Resource.Loading -> {
                    state.value = state.value.copy(
                        isLoading = true,
                        isError = false
                    )
                }
            }
        }.launchIn(viewModelScope) // starts the flow
    }

    private fun getCoinPrice(coinId: String) {
        viewModelScope.launch {
            when (val latestOHLCVResult = getLatestOHLCV(coinId)) {
                is Resource.Success -> {

                    //println(latestOHLCVResult.data) // leave for debug

                    // Check for null data
                    if (latestOHLCVResult.data == null) {
                        state.value = state.value.copy(
                            isError = true,
                            errorMessage = "Error getting price data. Likely API Limit reached.",
                            coinInfo = state.value.coinInfo?.copy(
                                ohlcvToday = null
                            ),
                        )
                        return@launch
                    }

                    state.value = state.value.copy(
                        coinInfo = state.value.coinInfo?.copy(
                            ohlcvToday = latestOHLCVResult.data.copy()
                        ),
                    )
                }
                is Resource.Error -> {
                    state.value = state.value.copy(
                        isError = true,
                        errorMessage = latestOHLCVResult.message ?: "Error getting prices"
                    )
                }
                is Resource.Loading -> {}
            }
        }
    }

    private suspend fun getLatestOHLCV(
        coinId: String
    ): Resource<CoinOHLCVItemDTO> {
        val jsonDecodeLenientIgnoreUnknown = Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
        return withContext(kotlinx.coroutines.Dispatchers.IO) {
            try {
                val response =
                    URL(
                        "https://api.coinpaprika.com/v1/coins/${coinId}/ohlcv/latest"
                    ).readText()

                //println(response) // leave for debug purposes

                // Get the OHLCV prices for today from the response
                Resource.Success(
                    jsonDecodeLenientIgnoreUnknown
                        .decodeFromString<CoinOHLCVItemsDTO>(response)[0]
                )
            } catch (e: Exception) {
                Resource.Error(message = "Error getting price data. Likely API Limit reached.")
            }
        }
    }
}

