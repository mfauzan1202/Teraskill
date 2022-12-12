package id.co.mka.teraskill.ui.main.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.FragmentDashboardBinding
import id.co.mka.teraskill.utils.Preferences

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = Preferences(requireContext())
        val nickName = preferences.getValues("name")!!.split(" ")[0]

        binding.apply{
            Glide.with(requireContext())
                .load(preferences.getValues("avatar"))
                .into(ivProfilePicture)
            tvName.text = resources.getString(R.string.hello, nickName)

            val dashboardPagerAdapter = DashboardPagerAdapter(activity as AppCompatActivity)
            val viewPager: ViewPager2 = viewPager
                viewPager.adapter = dashboardPagerAdapter
            val tabs: TabLayout = tabLayout
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3,
            R.string.tab_text_4
        )
    }
}