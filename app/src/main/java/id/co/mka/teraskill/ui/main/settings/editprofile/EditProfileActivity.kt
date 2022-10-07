package id.co.mka.teraskill.ui.main.settings.editprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}