package com.mobeedev.vama.album.data.datasource.remote

import com.mobeedev.vama.album.data.model.AlbumFeedDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumService {

    @GET("api/v2/us/music/most-played/100/albums.json")
    suspend fun getMostPlayedAlbum(): Response<AlbumFeedDto>
}