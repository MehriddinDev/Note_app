package uz.gita.noteapp_mehriddinn.presentation.screen.addNote.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.noteapp_mehriddinn.data.model.NoteData
import uz.gita.noteapp_mehriddinn.domain.repasitory.impl.NoteRepastoryImpl
import uz.gita.noteapp_mehriddinn.presentation.screen.addNote.viewmodel.AddNoteViewModel

class AddNoteViewModelImpl : ViewModel(),AddNoteViewModel {
    private val repastory = NoteRepastoryImpl.getInstance()

    private val _stateDoneBtn = MutableLiveData<Boolean>()
    val stateDoneBtn : LiveData<Boolean> = _stateDoneBtn

    private val _clickDoneLiveData = MutableLiveData<Unit>()
    val clickDoneLiveData : LiveData<Unit> = _clickDoneLiveData

    fun setStateDoneBtn(count:String){
        if (count.length > 0)
        _stateDoneBtn.value = true
        else _stateDoneBtn.value = false
    }

    fun addNote(id:Long,title:String,content:String,date:String){
        repastory.addNote(NoteData(id,title,content,date,0))
        _clickDoneLiveData.value = Unit
    }

    override fun update(noteData: NoteData) {
        repastory.update(noteData)
    }


}
