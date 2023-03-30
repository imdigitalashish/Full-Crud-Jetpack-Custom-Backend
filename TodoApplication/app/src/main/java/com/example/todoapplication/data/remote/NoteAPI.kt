package com.example.todoapplication.data.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NoteAPI {


    @GET("notes")
    suspend fun getNotes(): List<NoteDto>


    @POST("add_note")
    fun createNote(
        @Query("note_title") note_title: String,
        @Query("note_content") note_content: String,
    ): Call<NoteResponse>

}
