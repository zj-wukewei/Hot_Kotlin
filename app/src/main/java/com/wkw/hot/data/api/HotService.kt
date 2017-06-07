package com.wkw.hot.data.api

import com.google.gson.GsonBuilder
import com.wkw.hot.domain.DomainConstanst.Companion.Base_Url
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by hzwukewei on 2017-6-6.
 */
object HotService {


    val mRetrofit: Retrofit

    init {
        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create()
        val builder = Retrofit.Builder()
                .baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getClient())
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        mRetrofit = builder.build()
    }

    private fun getClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .hostnameVerifier { hostname, session -> true }
                .cookieJar(object : CookieJar {
                    private val cookieStore = HashMap<HttpUrl, List<Cookie>>()

                    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                        cookieStore.put(HttpUrl.parse(url.host()), cookies)
                    }

                    override fun loadForRequest(url: HttpUrl): List<Cookie> {
                        val cookies = cookieStore.get(HttpUrl.parse(url.host()))
                        return cookies ?: ArrayList()
                    }
                })
                .build()
    }

    fun <T> createApi(clazz: Class<T>): T {
        return mRetrofit.create(clazz)
    }

}