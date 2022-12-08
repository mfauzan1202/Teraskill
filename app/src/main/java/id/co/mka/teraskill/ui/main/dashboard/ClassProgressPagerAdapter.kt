package id.co.mka.teraskill.ui.main.dashboard

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ClassProgressPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ClassProgressFragment()
            1 -> fragment = ClassProgressFragment() //TODO: change to "Kelas Saya"
            2 -> fragment = ClassProgressFragment() //TODO: change to "Portofolio"
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 3
    }

}