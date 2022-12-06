package id.co.mka.teraskill.ui.splash.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.co.mka.teraskill.databinding.FragmentSecondOnBoardingBinding


class SecondOnBoardingFragment : Fragment() {
    private var _binding: FragmentSecondOnBoardingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSecondOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnNext.setOnClickListener {
                findNavController().navigate(SecondOnBoardingFragmentDirections.actionSecondOnBoardingFragmentToThirdOnBoardingFragment())
            }
            tvSkip.setOnClickListener {
                findNavController().navigate(SecondOnBoardingFragmentDirections.actionSecondOnBoardingFragmentToAuthActivity())
                requireActivity().finish()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}