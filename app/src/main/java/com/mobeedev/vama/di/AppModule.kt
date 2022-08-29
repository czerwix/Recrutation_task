package com.mobeedev.vama.di

import com.mobeedev.vama.album.data.datasource.remote.AlbumService
import com.mobeedev.vama.common.config.EnvironmentConfiguration
import com.mobeedev.vama.database.VamaDB
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class AppModule {

    val dataModule = module {
        single { VamaDB.getInstance(androidContext()) }

        single { get<VamaDB>().albumDao() }
        single { get<VamaDB>().genreDao() }
    }

    val configModule = module {
        single { EnvironmentConfiguration }
    }

    val remoteModule = module {
        single {
            val timeout = get<EnvironmentConfiguration>().maxTimeout

            OkHttpClient.Builder()
                .apply {
                    connectTimeout(timeout, TimeUnit.SECONDS).readTimeout(timeout, TimeUnit.SECONDS)
                }
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .apply {
                            //todo remove if not in debug flavour
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                .build()
        }

        single<Retrofit>() {
            Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(get()))
                .baseUrl(get<EnvironmentConfiguration>().baseUrl)
                .client(get())
                .build()
        }
    }

    val apiModule = module {
        single { get<Retrofit>().create(AlbumService::class.java) }
    }

    val moshiModule = module {
        single { Moshi.Builder().build() }
    }
}