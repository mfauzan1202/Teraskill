package id.co.mka.teraskill.ui.main.settings

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.FragmentSettingBinding
import id.co.mka.teraskill.ui.auth.AuthActivity
import id.co.mka.teraskill.ui.main.settings.editprofile.EditProfileActivity
import id.co.mka.teraskill.ui.main.settings.transaction_history.TransactionHistoryActivity
import id.co.mka.teraskill.utils.Preferences


class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = Preferences(requireContext())
        val nickName = preferences.getValues("name")!!.split(" ")[0]

        binding.apply {
            Glide.with(requireContext())
                .load(preferences.getValues("avatar"))
                .into(ivProfilePicture)

            tvName.text = resources.getString(R.string.hello, nickName)
            btnEditProfile.setOnClickListener {
                startActivity(Intent(requireContext(), EditProfileActivity::class.java))
            }
            btnTransactionHistory.setOnClickListener {
                startActivity(Intent(requireContext(), TransactionHistoryActivity::class.java))
            }
            btnLogOut.setOnClickListener {
                Preferences(requireContext()).clearValues()
                startActivity(Intent(requireContext(), AuthActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}