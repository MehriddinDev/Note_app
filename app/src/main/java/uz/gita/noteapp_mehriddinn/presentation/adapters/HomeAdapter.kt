package uz.gita.noteapp_mehriddinn.presentation.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.noteapp_mehriddinn.data.model.NoteData
import uz.gita.noteapp_mehriddinn.databinding.ItemNoteBinding
import uz.gita.noteapp_mehriddinn.utils.myApply

class HomeAdapter: ListAdapter<NoteData, HomeAdapter.Holder>(NoteCallBack) {
    private lateinit var deleteListener:((NoteData)->Unit)
    private lateinit var updateListener:((NoteData) -> Unit)
    fun setUpdateListener(k:(NoteData)->Unit){
        updateListener = k
    }
    fun setDeleteListener(k:(NoteData)->Unit){
        deleteListener = k
    }

    inner class Holder(private val binding:ItemNoteBinding):RecyclerView.ViewHolder(binding.root){

        init {
            binding.btnDelete.setOnClickListener { deleteListener.invoke(getItem(adapterPosition)) }

            binding.root.setOnClickListener {
                val item = getItem(adapterPosition)
                updateListener.invoke(item)
            }
        }

        fun bind()=binding.myApply{
            val item = getItem(adapterPosition)

            txtTitle.text = item.title
            txtContent.text = item.content.parseAsHtml()
            txtData.text = item.createdAt
        }
    }

    object NoteCallBack:ItemCallback<NoteData>(){
        override fun areItemsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind()
    }
}
