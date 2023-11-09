package uz.gita.noteapp_mehriddinn.presentation.screen.home.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.noteapp_mehriddinn.data.model.NoteData
import uz.gita.noteapp_mehriddinn.domain.repasitory.impl.NoteRepastoryImpl
import uz.gita.noteapp_mehriddinn.presentation.screen.home.viewmodel.HomeViewModel

class HomeViewModelImpl : ViewModel(), HomeViewModel {
    private val repastory = NoteRepastoryImpl.getInstance()

    val notesLiveData: LiveData<List<NoteData>> = repastory.getNotes()

    private val _openAddScreen = MutableLiveData<Unit>()
    override val openAddScreen: LiveData<Unit> = _openAddScreen
    private val _openAddScreenForUpdate= MutableLiveData<NoteData>()
    override val openAddScreenForUpdate = _openAddScreenForUpdate
    override val searchedWordsLiveData= MutableLiveData<List<NoteData>>()

    override fun openAddScreen() {
        _openAddScreen.value = Unit
    }

    override fun deleteNote(note: NoteData) {
        repastory.update(NoteData(note.id, note.title, note.content, note.createdAt, 1))
    }

    override fun searchingNote(s: String) {
        searchedWordsLiveData.value = repastory.getNotesByQuery(s)
    }

    override fun openAddScreenForUpdate(note: NoteData) {
        _openAddScreenForUpdate.value = note
    }

}