package id.co.mka.teraskill.ui.classroom.class_exam

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.mka.teraskill.data.responses.ExamResponse
import id.co.mka.teraskill.databinding.ItemAnswerBinding

class ClassExamAdapter(private val onClickListener: (String, Boolean, Boolean) -> Unit) :
    RecyclerView.Adapter<ClassExamAdapter.ViewHolder>() {

    private val listQuestion = ArrayList<ExamResponse.OptionExamsItem>()
    private var rbQuestionActive: String? = null

    fun setQuestionAnswer(data: List<ExamResponse.OptionExamsItem>){
        listQuestion.clear()
        listQuestion.addAll(data)
        notifyDataSetChanged()
    }

    fun setQuestionAnswerChoose(uuid: String){
        rbQuestionActive = uuid
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding: ItemAnswerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ExamResponse.OptionExamsItem){
            binding.apply {
                rbAnswer.isChecked = false
                rbAnswer.text = data.content
                rbAnswer.setOnClickListener {
                    if(data.correctAnswer!!){
                        onClickListener(data.uuid!!, true, true)
                    }
                    else{
                        onClickListener(data.uuid!!, true, false)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (rbQuestionActive != listQuestion[position].uuid){
            holder.bind(listQuestion[position])
        }
    }

    override fun getItemCount(): Int = listQuestion.size
}