package uz.gita.noteapp_mehriddinn.data.model

import uz.gita.noteapp_mehriddinn.data.source.local.entities.NoteEntitiy

data class NoteData(
    val id:Long ,
    val title:String,
    val content:String,
    val createdAt: String,
    val onTrash:Int = 0
):java.io.Serializable

fun NoteData.toNoteEntity() = NoteEntitiy(id, title, content, createdAt, onTrash)
