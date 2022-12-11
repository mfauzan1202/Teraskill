package id.co.mka.teraskill.ui.main.settings.transaction_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import id.co.mka.teraskill.R
import id.co.mka.teraskill.data.responses.KelasUserItem
import id.co.mka.teraskill.data.responses.TransactionHistoryResponse
import id.co.mka.teraskill.databinding.ItemTransactionHistoryBinding

class TransactionHistoryAdapter(
    private val listTransaction: List<KelasUserItem>,
    val clickListener: () -> Unit
) : RecyclerView.Adapter<TransactionHistoryAdapter.TransactionHistoryViewHolder>() {

    inner class TransactionHistoryViewHolder(val binding: ItemTransactionHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: KelasUserItem, holder: TransactionHistoryViewHolder) {
            val context = holder.itemView.context
            binding.apply {
                tvClassTitle.text = transaction.kelasTeraskill?.name
                val transformDate =
                    transaction.tanggalPembayaran?.split("T".toRegex())?.toTypedArray()
                val date = transformDate?.get(0)?.split("-".toRegex())?.toTypedArray()
                if (transaction.tanggalPembayaran != null) {
                    tvTransactionDate.text = "${date?.get(2)}-${date?.get(1)}-${date?.get(0)}"
                } else {
                    tvTransactionDate.text = "Belum ada pembayaran"
                }
                tvPrice.text =
                    context.getString(R.string.price_format, transaction.nominal.toString())
                if (transaction.status == "Menunggu Pembayaran") {
                    btnStatus.background =
                        AppCompatResources.getDrawable(context, R.drawable.button_red)
                    btnStatus.text = "Belum Dibayar"
                }else if (transaction.status == "Pengecekan Pembayaran") {
                    btnStatus.background =
                        AppCompatResources.getDrawable(context, R.drawable.button_blue)
                    btnStatus.text = "Proses Verifikasi"
                }else if (transaction.status == "Lunas") {
                    btnStatus.background =
                        AppCompatResources.getDrawable(context, R.drawable.button_green)
                    btnStatus.text = "Terverifikasi"
                }
                itemView.setOnClickListener {
                    clickListener.invoke()
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionHistoryViewHolder {
        val view = ItemTransactionHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionHistoryViewHolder, position: Int) {
        holder.bind(listTransaction[position], holder)
    }

    override fun getItemCount(): Int = listTransaction.size

}