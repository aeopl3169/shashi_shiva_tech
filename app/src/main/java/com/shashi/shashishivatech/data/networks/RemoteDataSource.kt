package com.shashi.shashishivatech.data.networks

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.BuildConfig
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.shashi.shashishivatech.api.ApplicationsAPI
import com.shashi.shashishivatech.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class RemoteDataSource @Inject constructor() {

    companion object {
        const val FINAL_URL = BASE_URL + "api/v1/"
    }

    fun <Api> buildApi(
        api: Class<Api>,
        context: Context,
    ): Api {
        return Retrofit.Builder()
            .baseUrl(FINAL_URL)
            .client(getRetrofitClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }


    private fun buildTokenApi(context: Context): ApplicationsAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getRetrofitClient(context = context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApplicationsAPI::class.java)
    }


    private fun getRetrofitClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(NetworkConnectionInterceptor(context))
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .addInterceptor { chain ->
                try {

                    chain.proceed(chain.request().newBuilder().also {
                        it.addHeader("Content-Type", "application/json")
                    }.build())

                } catch (e: SocketTimeoutException) {
                    Log.d("TAG", "getRetrofitClient: "+e.message)
                    throw IOException()
                }

            }
            .also { client ->
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
               }
            }.build()
    }
}