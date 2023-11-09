package uz.gita.noteapp_mehriddinn.presentation.screen.home.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.noteapp_mehriddinn.data.model.NoteData

interface HomeViewModel {
    val openAddScreen:LiveData<Unit>
    val openAddScreenForUpdate:LiveData<NoteData>
val searchedWordsLiveData:LiveData<List<NoteData>>
    fun openAddScreen()
    fun deleteNote(noteData: NoteData)
    fun searchingNote(s:String)
    fun openAddScreenForUpdate(note: NoteData)
}