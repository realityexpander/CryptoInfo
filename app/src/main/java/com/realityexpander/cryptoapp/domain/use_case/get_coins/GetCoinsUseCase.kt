package com.realityexpander.cryptoapp.domain.use_case.get_coins

import com.realityexpander.cryptoapp.common.Resource
import com.realityexpander.cryptoapp.domain.models.Coin
import com.realityexpander.cryptoapp.domain.repository.CoinRepositoryInterface
import com.realityexpander.cryptoapp.mappers.toCoins
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject


// "Use case" should have ONLY ONE RESPONSIBILITY (ie: one function, the invoke)
class GetCoinsUseCase @Inject constructor(
    private val coinsRepo: CoinRepositoryInterface
) {
    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())

            val coins = coinsRepo.getCoinDTOs()
            emit(Resource.Success(coins.toCoins()))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage
                ?: "An unexpected Error occurred.")
            )
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage
                ?: "Could not reach server, check internet connection.")
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage
                ?: "Unknown Error")
            )
        }
    }
}
