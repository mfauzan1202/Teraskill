package id.co.mka.teraskill.ui.classroom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.ActivityClassPreviewBinding
import id.co.mka.teraskill.utils.Preferences

class ClassPreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClassPreviewBinding
    private lateinit var viewModel: ClassPreviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uuid = intent.getStringExtra(EXTRA_ID)
        val token = Preferences(this).getValues("token")

        viewModel = ClassPreviewViewModel()

        viewModel.getDetailClass(token!!, uuid!!).observe(this) { it ->
            if (it != null) {
                binding.apply {
                    tvClassTitle.text = it.name
                    tvClassDesc.text = it.about
                    tvTotalModule.text =
                        getString(R.string.total_modul, it.jmlMateriText.toString())
                    tvTotalVideo.text =
                        getString(R.string.total_video, it.jmlMateriVideo.toString())

                    it.user.let { user ->
                        tvUser.text = user.name
                        user.pendaftaranMentor.let { mentor ->
                            tvJobDivision.text = mentor.job
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}