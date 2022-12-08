package id.co.mka.teraskill.ui.classroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.co.mka.teraskill.databinding.ActivityModulTeksBinding


class ModulTeksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModulTeksBinding
    private lateinit var viewModel: ModulTeksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModulTeksBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}