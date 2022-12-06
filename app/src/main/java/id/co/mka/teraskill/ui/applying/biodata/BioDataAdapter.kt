package id.co.mka.teraskill.ui.applying.biodata

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.mka.teraskill.databinding.ItemListBankBinding

class BioDataAdapter(private val bankList: ArrayList<String>, val clickListener: (String) -> Unit): RecyclerView.Adapter<BioDataAdapter.MyClassViewHolder>() {

    inner class MyClassViewHolder(val binding: ItemListBankBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bank: String) {
            binding.apply {
                tvBankName.text = bank
            }
            itemView.setOnClickListener{
                clickListener.invoke(bank)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyClassViewHolder {
        val view = ItemListBankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyClassViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyClassViewHolder, position: Int) {
        holder.bind(bankList[position])
    }

    override fun getItemCount(): Int = bankList.size
}