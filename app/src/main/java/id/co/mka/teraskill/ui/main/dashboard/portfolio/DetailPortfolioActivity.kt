package id.co.mka.teraskill.ui.main.dashboard.portfolio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.ActivityDetailPortfolioBinding

class DetailPortfolioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPortfolioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPortfolioBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}