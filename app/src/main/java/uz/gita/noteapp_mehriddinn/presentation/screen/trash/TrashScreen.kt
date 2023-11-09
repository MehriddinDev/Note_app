package uz.gita.noteapp_mehriddinn.presentation.screen.trash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.noteapp_mehriddinn.R
import uz.gita.noteapp_mehriddinn.data.model.NoteData
import uz.gita.noteapp_mehriddinn.databinding.ScreenTrashBinding
import uz.gita.noteapp_mehriddinn.presentation.adapters.TrashAdapter
import uz.gita.noteapp_mehriddinn.presentation.screen.trash.viewmodel.impl.TrashViewModelImpl
import uz.gita.noteapp_mehriddinn.utils.myApply

class TrashScreen : Fragment(R.layout.screen_trash) {
    private val viewModel by viewModels<TrashViewModelImpl>()
    private val binding by viewBinding(ScreenTrashBinding::bind)
    private val trashAdapter by lazy { TrashAdapter() }
    private var handler = Handler(Looper.getMainLooper())

    val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.trash_menu,menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when(menuItem.itemId){
                R.id.clear ->{
                    viewModel.deleteAllNotesInTrash()
                    return true
                }
                R.id.search->{
                    if (binding.searchView.visibility == View.GONE) {
                        binding.searchView.visibility = View.VISIBLE
                        binding.searchViewParent.visibility = View.VISIBLE
                        binding.searchView.requestFocus()
                        val imm =
                            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.showSoftInput(binding.searchView, InputMethodManager.SHOW_IMPLICIT)
                    } else {
                        binding.searchView.visibility = View.GONE
                        binding.searchViewParent.visibility = View.GONE
                    }
                    return true
                }
                else ->false
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        requireActivity().addMenuProvider(menuProvider)

        noteList.adapter = trashAdapter
        noteList.layoutManager = LinearLayoutManager(requireContext())

        trashAdapter.setDeleteListener {
            viewModel.update(it)
        }

        ///Observers
        viewModel.notesLiveData.observe(viewLifecycleOwner,notesObserver)

        //SearchView impl
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("TTT", "Submit")
                handler.removeCallbacksAndMessages(null)
                query?.let {

                    if(query.isNotBlank()){
                        viewModel.searchingNote("%${it}%")
                        viewModel.searchedNotes.observe(viewLifecycleOwner,searchingNotesObserver)
                    }else{
                        viewModel.notesLiveData.observe(viewLifecycleOwner, notesObserver)
                    }
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                Log.d("TTT", "change")
                handler.postDelayed({
                    newText?.let {
                        if(newText.isNotBlank()){

                            viewModel.searchingNote("%${it}%")
                            viewModel.searchedNotes.observe(viewLifecycleOwner,searchingNotesObserver)
                        }else{
                            viewModel.notesLiveData.observe(viewLifecycleOwner, Observer {
                                trashAdapter.submitList(it)
                            })
                        }
                    }
                }, 100)

                return true
            }

        })

    }

    private val notesObserver = Observer<List<NoteData>>{trashAdapter.submitList(it)}
    private val searchingNotesObserver = Observer<List<NoteData>>{trashAdapter.submitList(it) }

    override fun onPause() {
        super.onPause()
        requireActivity().removeMenuProvider(menuProvider)

    }

}