package id.co.mka.teraskill.ui.classroom.class_theory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import id.co.mka.teraskill.R
import id.co.mka.teraskill.data.responses.ModulResponse
import id.co.mka.teraskill.databinding.ItemListSubchapterBinding


class ClassSubChapterAdapter(private val onClickListener: ((ModulResponse) -> Unit)? = null) :
    RecyclerView.Adapter<ClassSubChapterAdapter.ViewHolder>() {

    private var buttonActiveModulID: String? = null
    private var buttonActiveMateriID: String? = null
    lateinit var listSubChapter: List<ModulResponse>

    fun setButtonActive(uuidModul: String, uuidMateri: String= "") {
        buttonActiveModulID = uuidModul
        buttonActiveMateriID = uuidMateri
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemListSubchapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ModulResponse) {
            binding.apply {
                if (buttonActiveMateriID == data.uuid){
                    tvTitleSubchapter.text = data.title
                    tvTitleSubchapter.setTextColor(
                        AppCompatResources.getColorStateList(
                            itemView.context,
                            R.color.white
                        )
                    )
                    itemListSubchapter.backgroundTintList =
                        AppCompatResources.getColorStateList(
                            itemView.context,
                            R.color.primary_color
                        )
                    if (data.statusProgress == "readed"){
                        ivCheckSubchapter.visibility = View.VISIBLE
                        ivCheckSubchapter.setImageResource(R.drawable.ic_check_on)
                    }
                }
                else{
                    bindInactive(data)
                }
            }
        }

        fun bindInactive(data: ModulResponse) {
            binding.apply {
                tvTitleSubchapter.text = data.title
                tvTitleSubchapter.setTextColor(
                    AppCompatResources.getColorStateList(
                        itemView.context,
                        R.color.black
                    )
                )
                itemListSubchapter.backgroundTintList =
                    AppCompatResources.getColorStateList(
                        itemView.context,
                        R.color.soft_color_3
                    )
                if (data.statusProgress == "readed"){
                    ivCheckSubchapter.visibility = View.VISIBLE
                    ivCheckSubchapter.setImageResource(R.drawable.ic_check)
                } else {
                    ivCheckSubchapter.visibility = View.GONE
                }
            }
            itemView.setOnClickListener {
                onClickListener?.invoke(data)
                setButtonActive(data.modul.uuid, data.uuid)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemListSubchapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listSubChapter[position].modul.uuid == buttonActiveModulID) {
            holder.bind(listSubChapter[position])
        } else {
            holder.bindInactive(listSubChapter[position])
        }
    }

    override fun getItemCount(): Int = listSubChapter.size
}