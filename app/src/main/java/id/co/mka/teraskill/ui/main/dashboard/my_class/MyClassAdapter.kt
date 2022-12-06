package id.co.mka.teraskill.ui.main.dashboard.my_class

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.mka.teraskill.data.responses.MyClassResponse
import id.co.mka.teraskill.databinding.ItemMyClassBinding
import id.co.mka.teraskill.ui.classroom.ClassLearningActivity


class MyClassAdapter(private val myClassData: List<MyClassResponse>) :
    RecyclerView.Adapter<MyClassAdapter.MyClassViewHolder>() {

    inner class MyClassViewHolder(val binding: ItemMyClassBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(myClass: MyClassResponse) {
            binding.apply {
                tvTitle.text = myClass.kelasTeraskill.name
            }
            itemView.setOnClickListener {
                val previewPage = Intent(itemView.context, ClassLearningActivity::class.java)
                previewPage.putExtra(ClassLearningActivity.EXTRA_ID, myClass.kelasTeraskill.uuid)
                itemView.context.startActivity(previewPage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyClassViewHolder {
        val view = ItemMyClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyClassViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyClassViewHolder, position: Int) {
        holder.bind(myClassData[position])
    }

    override fun getItemCount(): Int = myClassData.size
}