package id.co.mka.teraskill.ui.applying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.co.mka.teraskill.databinding.FragmentVerificationProcessBinding


class VerificationProcessFragment : Fragment() {

    private var _binding: FragmentVerificationProcessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVerificationProcessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnUnderstand.setOnClickListener {
                requireActivity().finish()
            }
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Verifikasi Jadi Mentor"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}