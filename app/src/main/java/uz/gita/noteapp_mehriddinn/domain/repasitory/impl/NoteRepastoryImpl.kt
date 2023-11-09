package uz.gita.noteapp_mehriddinn.domain.repasitory.impl

import androidx.lifecycle.LiveData
import uz.gita.noteapp_mehriddinn.data.model.NoteData
import uz.gita.noteapp_mehriddinn.data.model.toNoteEntity
import uz.gita.noteapp_mehriddinn.data.source.local.NoteDatabase
import uz.gita.noteapp_mehriddinn.domain.repasitory.NoteRepastory

class NoteRepastoryImpl private constructor():NoteRepastory {

    companion object {
        private lateinit var repository: NoteRepastoryImpl

        fun getInstance(): NoteRepastoryImpl {
            if(!(::repository.isInitialized)) {
                repository = NoteRepastoryImpl()
            }
            return repository
        }
    }

private val noteDao = NoteDatabase.getInstance().getNotedao()

    override fun addNote(note: NoteData) {
        noteDao.addNote(note.toNoteEntity())
    }

    override fun delete(note: NoteData) {
        noteDao.delete(note.toNoteEntity())
    }

    override fun update(note: NoteData) {
        noteDao.update(note.toNoteEntity())
    }

    override fun deleteAllNotesInTrash() {
        noteDao.deleteAllNotesInTrash()
    }

    override fun getNotes(): LiveData<List<NoteData>> {
        return noteDao.getNotes()
    }

    override fun getNotesInTrash(): LiveData<List<NoteData>> {
        return noteDao.getNotesInTrash()
    }

    override fun getNotesInTrashByQuery(query: String): LiveData<List<NoteData>> {
        return noteDao.getNotesInTrashByQuery(query)
    }

    override fun getNotesByQuery(note: String?): List<NoteData> {
        return noteDao.getNotesByQuery(note)
    }
}