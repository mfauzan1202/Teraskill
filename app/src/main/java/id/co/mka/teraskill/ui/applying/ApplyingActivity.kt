package id.co.mka.teraskill.ui.applying

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.co.mka.teraskill.databinding.ActivityApplyingBinding


class ApplyingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApplyingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}