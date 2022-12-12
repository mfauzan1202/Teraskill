package id.co.mka.teraskill.ui.main.dashboard

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.co.mka.teraskill.ui.main.dashboard.class_progress.ClassProgressFragment
import id.co.mka.teraskill.ui.main.dashboard.my_class.MyClassFragment
import id.co.mka.teraskill.ui.main.dashboard.portfolio.PortfolioFragment

class DashboardPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ClassProgressFragment()
            1 -> fragment = MyClassFragment()
            2 -> fragment = PortfolioFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 4
    }

}