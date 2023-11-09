package uz.gita.noteapp_mehriddinn.presentation.screen.trash.viewmodel

import uz.gita.noteapp_mehriddinn.data.model.NoteData

interface TrashViewModel {

    fun deleteAllNotesInTrash()
    fun update(note: NoteData)
    fun searchingNote(s:String)
}