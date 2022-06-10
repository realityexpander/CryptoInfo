package com.realityexpander.cryptoapp.data.remote

import android.content.Context
import com.realityexpander.cryptoapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response

import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

/**
 * This will help us to test our networking code while a particular API is not implemented
 * yet on Backend side.
 */
const val SUCCESS_CODE = 200
const val FAILURE_CODE = 500

class MockInterceptor @Inject constructor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        if (BuildConfig.DEBUG) {
            val uri = chain.request() .url.toUri().toString()
            val responseString = when {
                // uri.endsWith("coins") -> getCoinsJson  // mock data from local val
                uri.endsWith("coins") -> getCoinsFileJson(context) // mock data from local file
                else -> return chain.proceed(chain.request()) // use a live request if not mocked
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(SUCCESS_CODE)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString.toByteArray()
                        .toResponseBody(("application/json").toMediaType())
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError("MockInterceptor is only meant for Testing Purposes and " +
                    "bound to be used only with DEBUG mode")
        }
    }
}

fun getCoinsFileJson(context: Context): String {
    return getFileJson(context, "coins.json")
}

fun getFileJson(context: Context, assetsFile: String): String {
    return try {
        val inputStream = context.assets.open("coins.json")
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()

        String(buffer)
    } catch (e: IOException) {
        e.printStackTrace()

        "[]" // default return an empty array
    }
}

val getCoinsJson = """
[
  {
    "id": "btc-bitcoin",
    "name": "Bitcoin",
    "symbol": "BTC",
    "rank": 1,
    "is_new": false,
    "is_active": true,
    "type": "coin"
  },
  {
    "id": "eth-ethereum",
    "name": "Ethereum",
    "symbol": "ETH",
    "rank": 2,
    "is_new": false,
    "is_active": true,
    "type": "coin"
  }
]
"""
    .trimIndent()

// Classic way (java)
//private fun InputStream.parseToString(): String = this.let {
//    val outputStream = ByteArrayOutputStream()
//
//    val buffer = ByteArray(1024)
//    var length: Int
//    try {
//        while (this.read(buffer).also { length = it } != -1) {
//            outputStream.write(buffer, 0, length)
//        }
//        outputStream.close()
//        this.close()
//    } catch (e: IOException) {
//        return@let ""
//    }
//
//    outputStream.toString()
//}