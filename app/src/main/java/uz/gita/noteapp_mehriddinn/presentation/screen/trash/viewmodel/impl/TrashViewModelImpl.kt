package uz.gita.noteapp_mehriddinn.presentation.screen.trash.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import uz.gita.noteapp_mehriddinn.data.model.NoteData
import uz.gita.noteapp_mehriddinn.domain.repasitory.impl.NoteRepastoryImpl
import uz.gita.noteapp_mehriddinn.presentation.screen.trash.viewmodel.TrashViewModel

class TrashViewModelImpl:ViewModel(),TrashViewModel {
    private val repastory = NoteRepastoryImpl.getInstance()
    val notesLiveData :LiveData<List<NoteData>> = repastory.getNotesInTrash()
    lateinit var searchedNotes:LiveData<List<NoteData>>
    override fun deleteAllNotesInTrash() {
        repastory.deleteAllNotesInTrash()
    }

    override fun update(note: NoteData) {
        repastory.update(NoteData(note.id, note.title, note.content, note.createdAt, 0))
    }

    override fun searchingNote(s: String) {
        searchedNotes = repastory.getNotesInTrashByQuery(s)
    }


}