package uz.gita.noteapp_mehriddinn.presentation.screen.addNote

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.noteapp_mehriddinn.R
import uz.gita.noteapp_mehriddinn.data.source.local.converter.DateConverter
import uz.gita.noteapp_mehriddinn.databinding.AddnoteScreenBinding
import uz.gita.noteapp_mehriddinn.presentation.screen.addNote.viewmodel.impl.AddNoteViewModelImpl
import uz.gita.noteapp_mehriddinn.utils.myApply
import java.util.*


class AddNoteScreen : Fragment(R.layout.addnote_screen) {
    private val viewModel by viewModels<AddNoteViewModelImpl>()
    private val binding by viewBinding(AddnoteScreenBinding::bind)
    private val args: AddNoteScreenArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
//Observers
        viewModel.stateDoneBtn.observe(viewLifecycleOwner, btnAddStateObserver)
        viewModel.clickDoneLiveData.observe(viewLifecycleOwner, btnAddObserver)
        val note = args.noteData

            editTxtTitle.text = Editable.Factory.getInstance().newEditable(note.title)
            richEditor.html = note.content
            binding.btnAdd.setTextColor(Color.parseColor("#FFBB33"))

        editTxtTitle.setTextAppearance(R.style.CustomTextStyle)
        editTxtTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setStateDoneBtn(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        btnAdd.setOnClickListener {
            var richEditor_: String = richEditor.html
            if (richEditor_.length == 0) richEditor_ = ""
            var id = 0L
             id = note.id
            viewModel.addNote(
                id,
                editTxtTitle.text.toString(),
                richEditor_,
                DateConverter.fromDateToTimeStamp(Date())
            )
        }

        richEditor.setPlaceholder("Begin writing...")

        italic.setOnClickListener {
            richEditor.setItalic()

            if (txtItalic.currentTextColor == -17613) {
                txtItalic.setTextColor(Color.BLACK)
            } else {
                txtItalic.setTextColor(Color.parseColor("#FFBB33"))
            }
        }
        bold.setOnClickListener {
            richEditor.setBold()

            if (txtBold.currentTextColor == -17613) {
                txtBold.setTextColor(Color.BLACK)
            } else {
                txtBold.setTextColor(Color.parseColor("#FFBB33"))
            }
        }
        under.setOnClickListener {
            richEditor.setUnderline()

            if (txtUnder.currentTextColor == -17613) {
                txtUnder.setTextColor(Color.BLACK)
            } else {
                txtUnder.setTextColor(Color.parseColor("#FFBB33"))
            }
        }
        btnRedo.setOnClickListener {

            richEditor.redo()
        }

        btnUndo.setOnClickListener {

            richEditor.undo()
        }
    }
/*
Observer<Boolean> {
        binding.btnAdd.isEnabled = it
        if (it)
            binding.btnAdd.setTextColor(Color.parseColor("#FFBB33"))
        else binding.btnAdd.setTextColor(Color.parseColor("#F1EEEE"))
    }*/

    private val btnAddStateObserver = androidx.lifecycle.Observer<Boolean>{
        binding.btnAdd.isEnabled = it
        if (it)
            binding.btnAdd.setTextColor(Color.parseColor("#FFBB33"))
        else binding.btnAdd.setTextColor(Color.parseColor("#F1EEEE"))
    }
    private val btnAddObserver = androidx.lifecycle.Observer<Unit> { findNavController().navigateUp() }
}