package id.co.mka.teraskill.ui.main.dashboard.portfolio.list_portfolio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.mka.teraskill.databinding.FragmentListPortfolioBinding
import id.co.mka.teraskill.utils.Resource
import id.co.mka.teraskill.utils.showLoading
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListPortfolioFragment : Fragment() {

    private var _binding: FragmentListPortfolioBinding? = null
    private val binding get() = _binding!!
    private val adapter = ListPortfolioAdapter()
    private val viewModel: ListPortfolioViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListPortfolioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
//        showLoading(true, requireContext())
        getListPortfolio()

        binding.btnAddPortfolio.setOnClickListener {
            findNavController().navigate(ListPortfolioFragmentDirections.actionListPortfolioFragmentToAddPortfolioFirstFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setAdapter() {
        binding.apply {
            rvPortfolio.adapter = adapter
            rvPortfolio.layoutManager = LinearLayoutManager(requireContext())
        }
    }
    private fun getListPortfolio(){
        binding.apply {
            viewModel.getListPortfolio().observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {
                        binding.apply {
//                            showLoading(false, requireContext())
                            adapter.setData(it.data!!)
                            rvPortfolio.visibility = View.VISIBLE
                        }
                    }
                    is Resource.Error -> {
                        binding.apply {
//                            showLoading(false, requireContext())
                            rvPortfolio.visibility = View.GONE
                        }
                    }
                    is Resource.Loading -> {
                        binding.apply {
//                            showLoading(true, requireContext())
                            rvPortfolio.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}