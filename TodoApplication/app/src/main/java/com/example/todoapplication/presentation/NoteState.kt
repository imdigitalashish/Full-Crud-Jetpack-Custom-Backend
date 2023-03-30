package com.example.todoapplication.presentation

import com.example.todoapplication.data.remote.NoteDto

data class NoteState(
    val data: List<NoteDto>? = null
)
