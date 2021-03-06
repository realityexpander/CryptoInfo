package com.realityexpander.cryptoapp.presentation.coin_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realityexpander.cryptoapp.common.Resource
import com.realityexpander.cryptoapp.domain.models.Coin
import com.realityexpander.cryptoapp.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

// ViewModel is necessary to keep the state of the view between configuration changes

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase // this has the repository injected
): ViewModel() {

    var state = mutableStateOf(CoinListState())
        private set

    init {
        getCoins()
    }

    private fun getCoins() {
//        getCoinsUseCase().map {
//            if(it is Resource.Error) {
//                it.message ?: "Error"
//            } else
//                "Success"
//        }.map {
//            it.length > 10
//        }.mapLatest {
//            println(it)
//        }

        getCoinsUseCase().onEach { result ->
//            val result2: Resource<*> = // keep for testing
//                Resource.Error<List<Coin>>(errorMessage = "", message = "Network error.")

            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        isError = false,
                        coins = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = result.message ?: "Unknown Error"
                    )
                }
                is Resource.Loading -> {
                    state.value = state.value.copy(
                        isLoading = true,
                        isError = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}