package id.co.mka.teraskill.ui.main.home.webinar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.co.mka.teraskill.databinding.ActivityWebinarRegistrationBinding
import id.co.mka.teraskill.ui.main.MainActivity
import id.co.mka.teraskill.utils.Preferences
import id.co.mka.teraskill.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class WebinarRegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebinarRegistrationBinding
    private val viewModel: WebinarRegistrationViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebinarRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(ID)

        val dataUser = Preferences(this)

        binding.apply {
            etName.setText(dataUser.getValues("name"))
            etEmail.setText(dataUser.getValues("email"))
            etNo.setText(dataUser.getValues("no_hp"))

            btnJoinWebinar.setOnClickListener {
                registerWebinar(id!!.toInt())
            }
        }
    }

    private fun registerWebinar(id: Int) {
        binding.apply {
            viewModel.registerWebinar(
                id,
                etName.text.toString(),
                etStatus.text.toString(),
                etEmail.text.toString(),
                etNo.text.toString()
            ).observe(this@WebinarRegistrationActivity) {
                when (it) {
                    is Resource.Success -> {
                        val intent =
                            Intent(this@WebinarRegistrationActivity, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    }
                    is Resource.Error -> TODO()
                    is Resource.Loading -> TODO()
                }
            }
        }
    }

    companion object {
        const val ID = "id"
    }
}