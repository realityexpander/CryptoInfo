package com.realityexpander.cryptoapp.presentation.coin_detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.realityexpander.cryptoapp.common.Constants.PARAM_COIN_ID
import com.realityexpander.cryptoapp.common.Resource
import com.realityexpander.cryptoapp.domain.use_case.get_coin.GetCoinUseCase
import com.realityexpander.cryptoapp.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

// ViewModel is necessary to keep the state of the view between configuration changes

@HiltViewModel
class CoinInfoListViewModel @Inject constructor(
    private val getCoinInfoUseCase: GetCoinUseCase, // this has the repository injected
    savedStateHandle: SavedStateHandle  // gets nav params (PARAM_COIN_ID = coinId)
) : ViewModel() {

    var state = mutableStateOf(CoinInfoState())
        private set

    init {
        savedStateHandle.get<String>(PARAM_COIN_ID)?.let { coinId ->
            getCoinInfo(coinId)
        }
    }

    private fun getCoinInfo(coinId: String) {

        getCoinInfoUseCase(coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        isError = false,
                        coinInfo = result.data
                    )
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
        }
    }
}