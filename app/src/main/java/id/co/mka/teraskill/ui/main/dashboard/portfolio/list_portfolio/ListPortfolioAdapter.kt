package id.co.mka.teraskill.ui.main.dashboard.portfolio.list_portfolio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.co.mka.teraskill.data.responses.PortfolioResponse
import id.co.mka.teraskill.databinding.ItemPortfolioBinding

class ListPortfolioAdapter: RecyclerView.Adapter<ListPortfolioAdapter.ViewHolder>() {

    private val listPortfolio = arrayListOf<PortfolioResponse>()

    fun setData(data: List<PortfolioResponse>) {
        listPortfolio.clear()
        listPortfolio.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemPortfolioBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data : PortfolioResponse) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(ivClassPicture)
                tvTitle.text = data.projectName
                tvRole.text = data.posisi
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPortfolioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPortfolio[position])
    }

    override fun getItemCount(): Int = listPortfolio.size
}