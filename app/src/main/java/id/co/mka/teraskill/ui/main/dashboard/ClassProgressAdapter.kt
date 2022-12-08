package id.co.mka.teraskill.ui.main.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.mka.teraskill.R
import id.co.mka.teraskill.data.responses.ListKelasItem
import id.co.mka.teraskill.databinding.ItemClassProgressBinding

class ClassProgressAdapter(private val progressList: List<ListKelasItem>) : RecyclerView.Adapter<ClassProgressAdapter.ClassProgressViewHolder>() {

    class ClassProgressViewHolder(val binding: ItemClassProgressBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(progress: ListKelasItem, holder: ClassProgressViewHolder){
            val context = holder.itemView.context
            binding.apply{
                tvTitle.text = progress.kelasName
                tvTotalVideo.text = context.getString(R.string.progress_video, progress.totalMateri.toString())
                tvWatchedVideo.text = context.getString(R.string.progress_video, progress.progress.toString())
                progressClass.progress = progress.prosentase
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassProgressViewHolder {
        val view = ItemClassProgressBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClassProgressViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClassProgressViewHolder, position: Int) {
        holder.bind(progressList[position], holder)
    }

    override fun getItemCount(): Int {
        return progressList.size
    }
}