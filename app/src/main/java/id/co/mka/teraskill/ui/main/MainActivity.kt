package id.co.mka.teraskill.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.ActivityMainBinding
import id.co.mka.teraskill.ui.main.dashboard.DashboardFragment
import id.co.mka.teraskill.ui.main.home.HomeFragment
import id.co.mka.teraskill.ui.main.settings.SettingFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnHome.setOnClickListener {
                switchFragment(it)
            }
            btnDashboard.setOnClickListener {
                switchFragment(it)
            }
            btnSettings.setOnClickListener {
                switchFragment(it)
            }
        }
    }

    private fun switchFragment(view: View) {
        var fragment: Fragment? = null

        binding.apply {
            val iconHomeOff = {
                btnHome.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_home_off),
                    null,
                    null
                )
                btnHome.setTextColor(getColor(this@MainActivity, R.color.gray_text))
            }
            val iconHomeOn = {
                btnHome.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_home_on),
                    null,
                    null
                )
                btnHome.setTextColor(getColor(this@MainActivity, R.color.primary_color))
            }
            val iconDashboardOff = {
                btnDashboard.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_dashboard_off),
                    null,
                    null
                )
                btnDashboard.setTextColor(getColor(this@MainActivity, R.color.gray_text))
            }
            val iconDashboardOn = {
                btnDashboard.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_dashboard_on),
                    null,
                    null
                )
                btnDashboard.setTextColor(getColor(this@MainActivity, R.color.primary_color))
            }
            val iconSettingsOff = {
                btnSettings.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_settings_off),
                    null,
                    null
                )
                btnSettings.setTextColor(getColor(this@MainActivity, R.color.gray_text))
            }
            val iconSettingsOn = {
                btnSettings.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_settings_on),
                    null,
                    null
                )
                btnSettings.setTextColor(getColor(this@MainActivity, R.color.primary_color))
            }
            when (view) {
                btnHome -> {
                    fragment = HomeFragment()
                    iconHomeOn()
                    iconDashboardOff()
                    iconSettingsOff()
                }
                btnDashboard -> {
                    fragment = DashboardFragment()
                    iconHomeOff()
                    iconDashboardOn()
                    iconSettingsOff()
                }
                btnSettings -> {
                    fragment = SettingFragment()
                    iconHomeOff()
                    iconDashboardOff()
                    iconSettingsOn()
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment,
                    fragment!!
                )
                .commit()
        }
    }
}