package com.getyourguide.berlintours.common

import com.getyourguide.berlintours.R
import com.getyourguide.berlintours.common.extensions.resBoolean
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.kibotu.logger.Logger.log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class NetworkModule {

    var baseUrl = "http://www.google.com"

    fun retrofit(): Retrofit = provideRetrofit(provideGson(), provideOkHttpClient(additionalHeader = createDefaultHeader(), logging = R.bool.enable_logging.resBoolean))

    private fun provideGson(): Gson = with(GsonBuilder()) {
        setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        create()
    }

    private fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()

    }

    // region ok http client

    private fun provideOkHttpClient(additionalHeader: Map<String, String>? = null, logging: Boolean = true): OkHttpClient {
        val client = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(1 * 10, TimeUnit.SECONDS)
                .writeTimeout(1 * 10, TimeUnit.SECONDS)
                .connectTimeout(1 * 10, TimeUnit.SECONDS)
                .addNetworkInterceptor {
                    it.proceed(it.request()
                            .newBuilder()
                            .apply {
                                additionalHeader?.let { headers ->
                                    for ((key, value) in headers) {
                                        log("header -> $key : $value")
                                        header(key, value)
                                    }
                                }
                            }
                            .build())
                }.apply {
                    if (logging)
                        addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { log(it) })
                                .apply {
                                    level = if (logging)
                                        HttpLoggingInterceptor.Level.BODY
                                    else
                                        HttpLoggingInterceptor.Level.NONE
                                })
                }

        return client.build()
    }

    // endregion

    companion object {

        const val HEADER_KEY = "X-Authorization"

        fun createDefaultHeader(): Map<String, String> {
            return mapOf(
                    "Content-Type" to "application/json; charset=utf-8"
            )
        }

        fun getHeaderToken(token: String): String = "Bearer $token"
    }

}