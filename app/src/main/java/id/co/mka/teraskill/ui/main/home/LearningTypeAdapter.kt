package id.co.mka.teraskill.ui.main.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.co.mka.teraskill.R
import id.co.mka.teraskill.data.responses.LearningPathResponse
import id.co.mka.teraskill.databinding.ItemClassBinding
import id.co.mka.teraskill.ui.classroom.ClassLearningActivity.Companion.EXTRA_ID
import id.co.mka.teraskill.ui.learning_path.LearningPathActivity

class LearningTypeAdapter :
    ListAdapter<LearningPathResponse, RecyclerView.ViewHolder>(differCallback) {

    inner class ViewHolder(val binding: ItemClassBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: LearningPathResponse) {
            binding.apply {
                itemName.text = data.name
                Glide.with(itemView.context)
                    .load(data.image)
                    .placeholder(R.drawable.image_dummy)
                    .into(itemImage)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, LearningPathActivity::class.java)
                intent.putExtra(EXTRA_ID, data.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemClassBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            (holder as ViewHolder).bind(data)
        }
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<LearningPathResponse>() {
            override fun areItemsTheSame(
                oldItem: LearningPathResponse,
                newItem: LearningPathResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: LearningPathResponse,
                newItem: LearningPathResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}