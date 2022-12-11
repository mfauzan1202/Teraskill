package id.co.mka.teraskill.ui.main.dashboard.webinar

import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.co.mka.teraskill.data.responses.WebinarResponse
import id.co.mka.teraskill.databinding.ItemWebinarsBinding
import id.co.mka.teraskill.ui.main.dashboard.my_class.MyClassAdapter

class WebinarAdapter(val onClickListener: (String) -> Unit) : RecyclerView.Adapter<WebinarAdapter.ViewHolder>() {

    private val listWebinar = ArrayList<WebinarResponse>()
    fun setData(data: List<WebinarResponse>) {
        listWebinar.clear()
        listWebinar.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemWebinarsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: WebinarResponse) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(data.imageBanner)
                    .into(ivItemImage)
                tvItemName.text = data.title
                val transformDate =
                    data.tanggal.split("T".toRegex()).toTypedArray()
                val date = transformDate[0].split("-".toRegex()).toTypedArray()
                itemDate.text = "${date[2]}-${date[1]}-${date[0]}"
            }
            itemView.setOnClickListener {
                onClickListener.invoke(data.uuid)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWebinarsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listWebinar[position])
    }

    override fun getItemCount(): Int = listWebinar.size
}