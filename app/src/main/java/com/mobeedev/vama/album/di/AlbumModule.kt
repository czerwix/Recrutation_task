package com.mobeedev.vama.album.di

import com.mobeedev.vama.album.data.datasource.local.AlbumLocalDataSource
import com.mobeedev.vama.album.data.repository.AlbumRepositoryImpl
import com.mobeedev.vama.album.domain.repository.AlbumRepository
import com.mobeedev.vama.album.domain.usecase.GetAlbumUseCase
import com.mobeedev.vama.album.domain.usecase.GetSavedTopAlbumDataUseCase
import com.mobeedev.vama.album.domain.usecase.LoadTopAlbumUseCase
import com.mobeedev.vama.album.ui.AlbumViewModel
import com.mobeedev.vama.album.ui.details.AlbumDetailsViewModel
import com.mobeedev.vama.di.common.ModuleLoader
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object AlbumModule : ModuleLoader() {

    override val modules: List<Module> =
        listOf(
            viewModelModule,
            useCaseModule,
            repositoryModule,
            dataSourceModule
        )

}

private val viewModelModule = module {
    viewModel {
        AlbumViewModel(
            application = get(),
            getSavedTopAlbumDataUseCase = get(),
            loadTopAlbumUseCase = get()
        )
    }

    viewModel { params ->
        AlbumDetailsViewModel(
            params.get(),
            get(),
            get()
        )
    }
}

private val useCaseModule = module {
    factory { GetSavedTopAlbumDataUseCase(albumRepository = get()) }
    factory { LoadTopAlbumUseCase(albumRepository = get()) }
    factory { GetAlbumUseCase(albumRepository = get()) }
}

private val repositoryModule = module {
    single<AlbumRepository> {
        AlbumRepositoryImpl(
            localAlbumDataSource = get(),
            remoteAlbumDataSource = get(),
        )
    }
}

private val dataSourceModule = module {
    single { AlbumLocalDataSource(albumDao = get(), genreDao = get()) }
}