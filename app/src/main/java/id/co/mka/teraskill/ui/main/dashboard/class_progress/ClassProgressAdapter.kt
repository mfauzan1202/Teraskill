package id.co.mka.teraskill.ui.main.dashboard.class_progress

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.mka.teraskill.R
import id.co.mka.teraskill.data.responses.ClassProgressResponse
import id.co.mka.teraskill.databinding.ItemClassProgressBinding
import id.co.mka.teraskill.ui.classroom.ClassLearningActivity

class ClassProgressAdapter(private val progressList: List<ClassProgressResponse.ListKelasItem>) : RecyclerView.Adapter<ClassProgressAdapter.ClassProgressViewHolder>() {

    class ClassProgressViewHolder(val binding: ItemClassProgressBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(progress: ClassProgressResponse.ListKelasItem, holder: ClassProgressViewHolder){
            val context = holder.itemView.context
            binding.apply{
                tvTitle.text = progress.kelasName
                tvTotalVideo.text = context.getString(R.string.progress_video, progress.totalMateri.toString())
                tvWatchedVideo.text = context.getString(R.string.progress_video, progress.progress.toString())
                progressClass.progress = progress.prosentase
            }
            itemView.setOnClickListener {
                val previewPage = Intent(itemView.context, ClassLearningActivity::class.java)
                previewPage.putExtra(ClassLearningActivity.EXTRA_ID, progress.uuid)
                itemView.context.startActivity(previewPage)
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