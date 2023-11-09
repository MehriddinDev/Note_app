package uz.gita.noteapp_mehriddinn.data.source.local

import android.content.Context
import androidx.room.*
import uz.gita.noteapp_mehriddinn.data.source.local.converter.DateConverter
import uz.gita.noteapp_mehriddinn.data.source.local.dao.NoteDao
import uz.gita.noteapp_mehriddinn.data.source.local.entities.NoteEntitiy

@Database(entities = [NoteEntitiy::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNotedao(): NoteDao

    companion object {
        private var database: NoteDatabase? = null
        fun init(context: Context) {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "Notes.db"
                )
                    .allowMainThreadQueries()
                    .build()
            }

        }

        fun getInstance(): NoteDatabase = database!!
    }
}