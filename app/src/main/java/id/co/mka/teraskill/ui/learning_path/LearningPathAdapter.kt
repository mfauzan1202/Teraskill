package id.co.mka.teraskill.ui.learning_path

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.co.mka.teraskill.data.responses.AllClassResponse
import id.co.mka.teraskill.databinding.ItemClassLearningpathBinding
import id.co.mka.teraskill.ui.classroom.ClassLearningActivity

class LearningPathAdapter :
    PagingDataAdapter<AllClassResponse, LearningPathAdapter.LearningPathViewHolder>(differCallback) {

    inner class LearningPathViewHolder(val binding: ItemClassLearningpathBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AllClassResponse) {
            binding.apply {
                tvItemName.text = data.name
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(ivItemImage)
            }
            itemView.setOnClickListener {
                val previewPage = Intent(itemView.context, ClassLearningActivity::class.java)
                previewPage.putExtra(ClassLearningActivity.EXTRA_ID, data.uuid)
                itemView.context.startActivity(previewPage)
            }
            itemView.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningPathViewHolder {
        return LearningPathViewHolder(
            ItemClassLearningpathBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: LearningPathViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<AllClassResponse>() {
            override fun areItemsTheSame(
                oldItem: AllClassResponse,
                newItem: AllClassResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: AllClassResponse,
                newItem: AllClassResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
