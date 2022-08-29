package com.mobeedev.vama.album.data.model

data class FeedDto(
    val country: String? = null,
    val copyright: String? = null,
    val author: AuthorDto? = null,
    val icon: String? = null,
    val links: List<LinksItemDto?>? = null,
    val id: String? = null,
    val title: String? = null,
    val updated: String? = null,
    val results: List<ResultsItemDto?>? = null
)
