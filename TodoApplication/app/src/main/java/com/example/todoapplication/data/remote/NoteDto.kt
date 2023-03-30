package com.example.todoapplication.data.remote

import com.squareup.moshi.Json




data class NoteDto(

    @field:Json(name = "id")
    val id: Double,


    @field:Json(name = "title")
    val title: String,


    @field:Json(name = "content")
    val content: String

)