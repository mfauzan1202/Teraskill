package id.co.mka.teraskill.ui.classroom.class_theory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.mka.teraskill.data.responses.ModulResponse
import id.co.mka.teraskill.data.responses.SingleClassResponse
import id.co.mka.teraskill.databinding.ItemListMainchapterBinding

class ClassTheoryAdapter(private val onClickListener: ((ModulResponse) -> Unit)? = null) :
    RecyclerView.Adapter<ClassTheoryAdapter.ViewHolder>() {

    private val listModule = HashMap<Int, SingleClassResponse.Response.ModulsItem>()
    private val listChapter = HashMap<Int, List<ModulResponse>>()
    private var activeButtonModuleID: String = ""
    private var activeButtonChapterID: String = ""

    fun setDataModule(dataModule: HashMap<Int, SingleClassResponse.Response.ModulsItem>) {
        listModule.clear()
        listModule.putAll(dataModule)
        notifyDataSetChanged()
    }

    fun setDataChapter(dataChapter: HashMap<Int, List<ModulResponse>>) {
        listChapter.clear()
        listChapter.putAll(dataChapter)
        notifyDataSetChanged()
    }

    fun setIDButtonActive(uuidModul: String, uuidMateri: String) {
        activeButtonModuleID = uuidModul
        activeButtonChapterID = uuidMateri
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemListMainchapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SingleClassResponse.Response.ModulsItem, position: Int) {
            binding.apply {
                tvTitleMainchapter.text = data.name
                rvSubchapter.layoutManager = LinearLayoutManager(itemView.context)
                val adapter = ClassSubChapterAdapter {
                    onClickListener?.invoke(it)
                    setIDButtonActive(it.modul.uuid, it.uuid)
                }
                adapter.listSubChapter = listChapter[position + 1]!!
                if (activeButtonModuleID == data.uuid) {
                    adapter.setButtonActive(activeButtonModuleID)
                    if (activeButtonChapterID != "") adapter.setButtonActive(
                        activeButtonModuleID,
                        activeButtonChapterID,
                    )
                }
                rvSubchapter.adapter = adapter

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemListMainchapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listModule.size == listChapter.size) {
            listModule[position + 1]?.let {
                holder.bind(it, position)
            }
        }
    }

    override fun getItemCount(): Int = listModule.size
}