package id.co.mka.teraskill.ui.classroom.class_preview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.mka.teraskill.data.responses.SingleClassResponse
import id.co.mka.teraskill.databinding.ItemListChapterBinding

class ClassPreviewAdapter(val onClickListener: (String) -> Unit) :
    RecyclerView.Adapter<ClassPreviewAdapter.ViewHolder>() {

    private val listModule = HashMap<Int, SingleClassResponse.Response.ModulsItem>()
    fun setData(data: List<SingleClassResponse.Response.ModulsItem>) {
        listModule.clear()
        for (i in data.indices) {
            listModule[data[i].urutan] = data[i]
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemListChapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SingleClassResponse.Response.ModulsItem, position: Int) {
            binding.apply {
                tvListChapter.text = "${position + 1}. ${data.name}"
            }
            itemView.setOnClickListener {
                onClickListener.invoke(data.uuid)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListChapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listModule[position+1]?.let {
            holder.bind(it, position)
        }
    }

    override fun getItemCount(): Int = listModule.size
}
