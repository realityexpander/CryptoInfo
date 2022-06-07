package com.realityexpander.cryptoapp.presentation.coin_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.realityexpander.cryptoapp.common.Resource
import com.realityexpander.cryptoapp.domain.repository.CoinRepositoryInterface
import com.realityexpander.cryptoapp.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
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