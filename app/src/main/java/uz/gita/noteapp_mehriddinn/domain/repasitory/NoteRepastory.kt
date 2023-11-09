package uz.gita.noteapp_mehriddinn.domain.repasitory

import androidx.lifecycle.LiveData
import uz.gita.noteapp_mehriddinn.data.model.NoteData
interface NoteRepastory {

    fun addNote(note: NoteData)

    fun delete(note: NoteData)

    fun update(note: NoteData)

    fun deleteAllNotesInTrash()

    fun getNotes(): LiveData<List<NoteData>>

    fun getNotesInTrash(): LiveData<List<NoteData>>

    fun getNotesInTrashByQuery(query:String): LiveData<List<NoteData>>

    fun getNotesByQuery(note:String?): List<NoteData>
}