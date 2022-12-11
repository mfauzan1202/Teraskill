package id.co.mka.teraskill.ui.main.dashboard.webinar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.mka.teraskill.databinding.ActivityWebinarsBinding
import id.co.mka.teraskill.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class WebinarsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebinarsBinding
    private val viewModel: WebinarViewModel by viewModel()
    private val adapter: WebinarAdapter by lazy {
        WebinarAdapter {
//            val intent = Intent(this, DetailWebinarsActivity::class.java)
//            intent.putExtra(DetailWebinarsActivity.EXTRA_WEBINAR, it)
//            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebinarsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
        getWebinar()
    }

    private fun getWebinar() {
        viewModel.getWebinar().observe(this) {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        adapter.setData(it.data!!)
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }

    private fun setAdapter() {
        binding.apply {
            rvWebinars.adapter = adapter
            rvWebinars.layoutManager = GridLayoutManager(this@WebinarsActivity, 2)
        }
    }
}