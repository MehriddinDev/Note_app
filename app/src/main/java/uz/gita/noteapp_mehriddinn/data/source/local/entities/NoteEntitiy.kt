package uz.gita.noteapp_mehriddinn.data.source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntitiy(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val title:String,
    val content:String,
    val createdAt: String,
    val onTrash:Int = 0
    )
