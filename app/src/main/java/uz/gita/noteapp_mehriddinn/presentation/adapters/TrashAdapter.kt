package uz.gita.noteapp_mehriddinn.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.noteapp_mehriddinn.data.model.NoteData
import uz.gita.noteapp_mehriddinn.databinding.ItemNoteBinding
import uz.gita.noteapp_mehriddinn.utils.myApply

class TrashAdapter : ListAdapter<NoteData, TrashAdapter.Holder>(NoteCallBack) {

    private lateinit var deleteListener: ((NoteData) -> Unit)
    fun setDeleteListener(k: (NoteData) -> Unit) {
        deleteListener = k
    }

    inner class Holder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnDelete.setOnClickListener {
                deleteListener.invoke(getItem(adapterPosition))
            }
        }

        fun bind() = binding.myApply {
            val item = getItem(adapterPosition)
            txtTitle.text = item.title
            txtContent.text = item.content.parseAsHtml()
            txtData.text = item.createdAt
        }
    }

    object NoteCallBack : DiffUtil.ItemCallback<NoteData>() {
        override fun areItemsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TrashAdapter.Holder, position: Int) {
        holder.bind()
    }


}