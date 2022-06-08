package com.realityexpander.cryptoapp.domain.use_case.get_coin

import com.realityexpander.cryptoapp.common.Resource
import com.realityexpander.cryptoapp.domain.models.CoinInfo
import com.realityexpander.cryptoapp.domain.repository.CoinRepositoryInterface
import com.realityexpander.cryptoapp.mappers.toCoinInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject


// "Use case" should have ONLY ONE RESPONSIBILITY (ie: one function, the invoke)
class GetCoinInfoUseCase @Inject constructor(
    private val coinsRepo: CoinRepositoryInterface
) {
    operator fun invoke(coinId: String): Flow<Resource<CoinInfo>> = flow {
        try {
            emit(Resource.Loading())

            val coinInfo = coinsRepo.getCoinInfoDTO(coinId)
            emit(Resource.Success(coinInfo.toCoinInfo()))
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
