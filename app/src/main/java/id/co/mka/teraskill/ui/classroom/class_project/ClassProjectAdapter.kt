package id.co.mka.teraskill.ui.classroom.class_project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.mka.teraskill.R
import id.co.mka.teraskill.data.responses.ProjectResponse
import id.co.mka.teraskill.databinding.ItemSubmissionBinding

class ClassProjectAdapter(val onClickListener: (String, String) -> Unit) :
    RecyclerView.Adapter<ClassProjectAdapter.ViewHolder>() {

    private val listProject = ArrayList<ProjectResponse>()

    fun setData(data: List<ProjectResponse>) {
        listProject.clear()
        listProject.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemSubmissionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProjectResponse, position: Int) {
            binding.apply {
                tvListSubmission.text = "Submission ${position + 1}"
                ivStatus.setImageResource(
                    if (data.userAswerProjek != null) {
                        R.drawable.ic_rb_submission
                    } else {
                        R.drawable.ic_rb_submission_done
                    }
                )
            }
            itemView.setOnClickListener { onClickListener(data.uuid, data.soal) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemSubmissionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listProject[position], position)
    }

    override fun getItemCount(): Int = listProject.size
}