package uz.gita.noteapp_mehriddinn.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import uz.gita.noteapp_mehriddinn.data.model.NoteData
import uz.gita.noteapp_mehriddinn.data.source.local.entities.NoteEntitiy

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: NoteEntitiy)

    @Delete
    fun delete(note: NoteEntitiy)

    @Update
    fun update(note: NoteEntitiy)

    @Query("DELETE FROM NoteEntitiy WHERE onTrash = 1")
    fun deleteAllNotesInTrash()

    @Query("SELECT * FROM NoteEntitiy WHERE onTrash = 0")
    fun getNotes(): LiveData<List<NoteData>>

    @Query("SELECT * FROM NoteEntitiy WHERE onTrash = 1")
    fun getNotesInTrash(): LiveData<List<NoteData>>

    @Query("SELECT * FROM NoteEntitiy WHERE title LIKE :note AND onTrash = 1")
    fun getNotesInTrashByQuery(note:String?): LiveData<List<NoteData>>

    @Query("SELECT * FROM NoteEntitiy WHERE title LIKE :note AND onTrash = 0")
    fun getNotesByQuery(note:String?): List<NoteData>
}