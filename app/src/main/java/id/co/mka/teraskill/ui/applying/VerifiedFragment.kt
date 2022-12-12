package id.co.mka.teraskill.ui.applying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.FragmentVerifiedBinding

class VerifiedFragment : Fragment() {
    private var _binding: FragmentVerifiedBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVerifiedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnHome.setOnClickListener {
            requireActivity().finish()
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Verifikasi Jadi Mentor"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}