package id.co.mka.teraskill.ui.learning_path

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.ActivityLearningPathBinding
import id.co.mka.teraskill.utils.Preferences
import id.co.mka.teraskill.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class LearningPathActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLearningPathBinding
    private val viewModel: LearningPathViewModel by viewModel()

    var listLearningPath = HashMap<String, String>()
    var listNameLearningPath = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearningPathBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        getLearningPathData()

        val preferences = Preferences(this)
        binding.apply {

            spLearningPath.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selected = parent?.getItemAtPosition(position).toString()
                    if (selected == "Pilih Learning Path") {
                        getData("0")
                    } else {
                        val idLearningPath = listLearningPath.filterValues { it == selected }.keys
                        Log.d("TESSS", listLearningPath.toString())
                        Log.d("SSSET", idLearningPath.first())
                        Toast.makeText(
                            this@LearningPathActivity,
                            idLearningPath.first().toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        getData(idLearningPath.first())
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Toast.makeText(
                        this@LearningPathActivity,
                        "Pilih Learning Path",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            Glide.with(this@LearningPathActivity)
                .load(preferences.getValues("avatar"))
                .into(ivProfilePicture)
            tvName.text = preferences.getValues("name")

            rvLearningPath.layoutManager = GridLayoutManager(this@LearningPathActivity, 2)
        }
    }

    private fun getData(learningPathID: String = "0") {
        val adapter = LearningPathAdapter()

        binding.rvLearningPath.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter { adapter.retry() }
        )
        if (learningPathID != "0") {
            viewModel.classInfoByLearningPath(learningPathID).observe(this){
                adapter.submitData(lifecycle, it)
            }
        }else {
            viewModel.classInfo.observe(this) {
                adapter.submitData(lifecycle, it)
            }
        }
    }

    private fun getLearningPathData() {
        listLearningPath["0"] = "Pilih Learning Path"
        listNameLearningPath.add("Pilih Learning Path")

        viewModel.getLearningPath()
            .observe(this) { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { learningPathResponse ->
                            binding.apply {
                                repeat(learningPathResponse.size) {
                                    listLearningPath[learningPathResponse[it].uuid] =
                                        learningPathResponse[it].name
                                    listNameLearningPath.add(learningPathResponse[it].name)
                                }
                                val adapter = ArrayAdapter(
                                    this@LearningPathActivity,
                                    R.layout.item_spinner_learning_path,
                                    R.id.tv_learning_type,
                                    listNameLearningPath
                                )
                                spLearningPath.adapter = adapter
                            }
                        }
                    }
                    is Resource.Error -> {
                        response.message?.let { message ->
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    else -> {}
                }
            }
    }

    companion object {
        const val EXTRA_ID = 0
    }
}