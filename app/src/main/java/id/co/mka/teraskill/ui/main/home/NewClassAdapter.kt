package id.co.mka.teraskill.ui.main.home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.co.mka.teraskill.R
import id.co.mka.teraskill.data.responses.AllClassResponse
import id.co.mka.teraskill.databinding.ItemClassBinding
import id.co.mka.teraskill.ui.classroom.ClassLearningActivity
import id.co.mka.teraskill.ui.classroom.ClassLearningActivity.Companion.EXTRA_ID

class NewClassAdapter :
    PagingDataAdapter<AllClassResponse, NewClassAdapter.ViewHolder>(differCallback) {

    inner class ViewHolder(val binding: ItemClassBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AllClassResponse) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(data.image)
                    .placeholder(R.drawable.image_dummy)
                    .into(itemImage)
                itemName.text = data.name
            }

            itemView.setOnClickListener {
                val previewPage = Intent(itemView.context, ClassLearningActivity::class.java)
                previewPage.putExtra(EXTRA_ID, data.uuid)
                itemView.context.startActivity(previewPage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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