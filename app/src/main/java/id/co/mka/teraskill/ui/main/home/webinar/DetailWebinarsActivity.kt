package id.co.mka.teraskill.ui.main.home.webinar

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.co.mka.teraskill.databinding.ActivityDetailWebinarsBinding
import id.co.mka.teraskill.ui.main.home.webinar.WebinarRegistrationActivity.Companion.ID
import id.co.mka.teraskill.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailWebinarsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailWebinarsBinding
    private val viewModel: DetailWebinarViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWebinarsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uuid = intent.getStringExtra(EXTRA_UUID)
        getData(uuid!!)
    }

    private fun getData(uuid: String) {
        viewModel.getDetailWebinar(uuid).observe(this) { webinar ->
            if (webinar != null) {
                when (webinar) {
                    is Resource.Loading -> {
                        binding.apply {

                        }
                    }
                    is Resource.Success -> {
                        if (webinar.data != null) {
                            binding.apply {
                                Glide.with(this@DetailWebinarsActivity)
                                    .load(webinar.data.response.imagePoster)
                                    .into(ivPamflet)
                                webinarsType.text = webinar.data.response.learningPath.name
                                tvTitle.text = webinar.data.response.title
                                val startTime =
                                    webinar.data.response.start.split(":".toRegex()).toTypedArray()
                                val endTime =
                                    webinar.data.response.end.split(":".toRegex()).toTypedArray()
                                val transformDate =
                                    webinar.data.response.tanggal.split("T".toRegex()).toTypedArray()
                                val date = transformDate[0].split("-".toRegex()).toTypedArray()
                                tvDate.text = "${date[2]}-${date[1]}-${date[0]}"
                                tvTime.text =
                                    "${startTime[0]}:${startTime[1]} - ${endTime[0]}:${endTime[1]} WITA"
                                tvVia.text = webinar.data.response.media
                                tvType.text = webinar.data.response.tipe
                                tvOrganizer.text = webinar.data.response.penyelenggaraName

                                if (webinar.data.status.statusBeli == "lunas"){
                                    Toast.makeText(this@DetailWebinarsActivity, "Anda sudah terdaftar pada webinar ini", Toast.LENGTH_SHORT).show()
                                }else {
                                    btnJoinWebinar.setOnClickListener {
                                        val intent = Intent(this@DetailWebinarsActivity, WebinarRegistrationActivity::class.java)
                                        intent.putExtra(ID, webinar.data.response.id.toString())
                                        startActivity(intent)
                                    }
                                }
                            }
                        }

                    }
                    is Resource.Error -> {
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_UUID = "extra_uuid"
    }
}