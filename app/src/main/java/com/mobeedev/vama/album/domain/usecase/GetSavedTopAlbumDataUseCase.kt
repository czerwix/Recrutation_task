package com.mobeedev.vama.album.domain.usecase

import com.mobeedev.vama.album.domain.repository.AlbumRepository
import com.mobeedev.vama.album.domain.usecase.comon.NoParametersUseCase
import com.mobeedev.vama.album.ui.model.AlbumItem

class GetSavedTopAlbumDataUseCase(private val albumRepository: AlbumRepository) :
    NoParametersUseCase<List<AlbumItem>>() {

    override suspend fun run(): Result<List<AlbumItem>> = albumRepository.getLocalAlbumInfo()
}