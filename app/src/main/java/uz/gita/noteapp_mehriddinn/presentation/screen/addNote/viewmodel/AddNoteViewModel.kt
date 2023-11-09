package uz.gita.noteapp_mehriddinn.presentation.screen.addNote.viewmodel

import uz.gita.noteapp_mehriddinn.data.model.NoteData

interface AddNoteViewModel {
    fun update(noteData: NoteData)
}