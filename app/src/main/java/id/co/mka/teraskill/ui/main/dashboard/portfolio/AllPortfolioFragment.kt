package id.co.mka.teraskill.ui.main.dashboard.portfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.co.mka.teraskill.databinding.FragmentAllPortfolioBinding
import id.co.mka.teraskill.databinding.FragmentListPortfolioBinding

class AllPortfolioFragment : Fragment() {

    private var _binding: FragmentAllPortfolioBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAllPortfolioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnAddPortfolio.setOnClickListener {
//                findNavController().navigate(AllPortfolioFragmentDirections.actionListPortfolioFragmentToAddPortfolioFirstFragment())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}