package com.example.todoapplication.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.data.remote.NoteAPI
import com.example.todoapplication.data.remote.NoteResponse
import com.example.todoapplication.data.remote.PostNoteDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import okhttp3.internal.applyConnectionSpec
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteApi: NoteAPI
) : ViewModel() {


    var state by mutableStateOf(NoteState())
        private set

    fun loadNoteInfo() {


        viewModelScope.launch {


//            val call = noteApi.createNote("My Note", "Message from android")


            state = state.copy(
                data = noteApi.getNotes()
            )
        }
    }

    fun createNote(noteTitle: String, noteContent: String) {
        val call = noteApi.createNote(noteTitle, noteContent)

        call.enqueue(
            object : Callback<NoteResponse> {
                override fun onResponse(
                    call: Call<NoteResponse>,
                    response: Response<NoteResponse>
                ) {
                    Log.d("TAG", response.message())
                    viewModelScope.launch {
                        state = state.copy(
                            data = noteApi.getNotes()
                        )
                    }

                }

                override fun onFailure(call: Call<NoteResponse>, t: Throwable) {
                    Log.d("ERROR", "Something went wrong")
                }
            }
        )

    }


}