package uz.gita.noteapp_mehriddinn.app

import android.app.Application
import uz.gita.noteapp_mehriddinn.data.source.local.NoteDatabase

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        NoteDatabase.init(this)
    }
}