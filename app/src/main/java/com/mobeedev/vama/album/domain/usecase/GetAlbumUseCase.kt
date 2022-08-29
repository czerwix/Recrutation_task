package com.mobeedev.vama.album.domain.usecase

import com.mobeedev.vama.album.domain.repository.AlbumRepository
import com.mobeedev.vama.album.domain.usecase.comon.NoParametersUseCase
import com.mobeedev.vama.album.domain.usecase.comon.UseCase
import com.mobeedev.vama.album.ui.model.AlbumItem

class GetAlbumUseCase(private val albumRepository: AlbumRepository) :
    UseCase<AlbumItem, GetAlbumUseCase.Params>() {

    override suspend fun run(params: Params): Result<AlbumItem> = albumRepository.getAlbum(params.albumId)

    data class Params(val albumId:String)
}