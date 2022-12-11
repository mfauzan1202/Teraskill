package id.co.mka.teraskill.ui.main.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import id.co.mka.teraskill.data.dataclass.MenuButton
import id.co.mka.teraskill.databinding.ItemMenuButtonBinding
import id.co.mka.teraskill.ui.applying.ApplyingActivity
import id.co.mka.teraskill.ui.learning_path.LearningPathActivity
import id.co.mka.teraskill.ui.main.home.webinar.WebinarsActivity


class HomeAdapter(private val buttonData: List<MenuButton>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMenuButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(menuButton: MenuButton) {
            binding.apply {
                tvTitleButton.text = menuButton.title
                ibMenu.setImageResource(menuButton.drawable)
                itemView.setOnClickListener {
                    val context: Context = it.context
                    when (adapterPosition) {
                        0 -> {
                            val intent = Intent(context, LearningPathActivity::class.java)
                            context.startActivity(intent)
                        }
                        1 -> {
                            val intent = Intent(context, ApplyingActivity::class.java)
                            context.startActivity(intent)
                        }
                        2 -> {
                            val intent = Intent(context, WebinarsActivity::class.java)
                            context.startActivity(intent)
                        }
                        3 -> {
                            //TODO: Not Implemented yet
                            Toast.makeText(
                                context,
                                "Work in Progress, Comeback Later :)",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        4 -> {
                            //TODO: Not Implemented yet
                            Toast.makeText(
                                context,
                                "Work in Progress, Comeback Later :)",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemMenuButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(buttonData[position])
    }

    override fun getItemCount(): Int = buttonData.size
}