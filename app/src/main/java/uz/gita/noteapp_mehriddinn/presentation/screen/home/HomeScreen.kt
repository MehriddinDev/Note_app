package uz.gita.noteapp_mehriddinn.presentation.screen.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.noteapp_mehriddinn.R
import uz.gita.noteapp_mehriddinn.data.model.NoteData
import uz.gita.noteapp_mehriddinn.databinding.ScreenHomeBinding
import uz.gita.noteapp_mehriddinn.presentation.adapters.HomeAdapter
import uz.gita.noteapp_mehriddinn.presentation.screen.home.viewmodel.impl.HomeViewModelImpl
import uz.gita.noteapp_mehriddinn.utils.myApply

class HomeScreen : Fragment(R.layout.screen_home) {
    private val viewModel by viewModels<HomeViewModelImpl>()
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val homeAdapter by lazy { HomeAdapter() }
    private var handler = Handler(Looper.getMainLooper())
    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.home_menu, menu)
        }


        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.search -> {
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
                else -> false
            }
        }

    }

    //observers
    private val openAddScreenObserve = Observer<Unit> {
        findNavController().navigate(R.id.action_homeScreen_to_addNoteScreen)
    }


    private val notesObserver = Observer<List<NoteData>> { homeAdapter.submitList(it) }
    private val searchingNotesObserver = Observer<List<NoteData>>{homeAdapter.submitList(it) }
    private val openAddScreenForUpdateObserver = Observer<NoteData> {
        findNavController().navigate(HomeScreenDirections.actionHomeScreenToAddNoteScreen(it))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openAddScreen.observe(this, openAddScreenObserve)
        viewModel.openAddScreenForUpdate.observe(this,openAddScreenForUpdateObserver)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        requireActivity().addMenuProvider(menuProvider,viewLifecycleOwner,Lifecycle.State.RESUMED)

        noteList.adapter = homeAdapter
        noteList.layoutManager = LinearLayoutManager(requireContext())
//      Observers
        viewModel.notesLiveData.observe(viewLifecycleOwner, notesObserver)


        viewModel.searchedWordsLiveData.observe(viewLifecycleOwner,searchingNotesObserver)
        viewModel.notesLiveData.observe(viewLifecycleOwner, Observer {
            homeAdapter.submitList(it)
        })
        viewModel.notesLiveData.observe(viewLifecycleOwner, notesObserver)

//        ClickListener
        homeAdapter.setDeleteListener { viewModel.deleteNote(it) }
        homeAdapter.setUpdateListener {
            viewModel.openAddScreenForUpdate(it)
        }
        btnOpenAdd.setOnClickListener { viewModel.openAddScreen() }


        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                query?.let {
                    if(query.isNotBlank()){
                        viewModel.searchingNote("%${it}%")
                    }else{
                        //  hamma notelarni olgin
                        homeAdapter.submitList(viewModel.notesLiveData.value)
                    }
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    newText?.let {
                        if(newText.isNotBlank()){
                            viewModel.searchingNote("%${it}%")
                        }else{
                            Log.d("CCC","Kirdi = $newText")
                            homeAdapter.submitList(viewModel.notesLiveData.value)
                        }
                    }
                }, 100)

                return true
            }

        })
    }



    override fun onPause() {
        super.onPause()
        requireActivity().removeMenuProvider(menuProvider)
    }

}