package id.co.mka.teraskill.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.ActivityMainBinding
import id.co.mka.teraskill.ui.main.home.HomeFragment
import id.co.mka.teraskill.ui.main.settings.SettingFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnSettings.setOnClickListener {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.settingFragment, HomeFragment())
                transaction.addToBackStack(null)
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                transaction.commit()
            }
        }

    }
}