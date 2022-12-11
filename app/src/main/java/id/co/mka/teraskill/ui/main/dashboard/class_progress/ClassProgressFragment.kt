package id.co.mka.teraskill.ui.main.dashboard.class_progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.mka.teraskill.databinding.FragmentClassProgressBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClassProgressFragment : Fragment() {

    private var _binding: FragmentClassProgressBinding? = null
    private val binding get() = _binding!!
    private lateinit var classProgressAdapter: ClassProgressAdapter
    private val viewModel : ClassProgressViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentClassProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProgress().observe(viewLifecycleOwner) {
            if (it != null) {
                classProgressAdapter = ClassProgressAdapter(it.listKelas)
                binding.rvClassProgress.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = classProgressAdapter
                }
            }
            else{
                binding.apply {
                    rvClassProgress.visibility = View.GONE
                    tvEmpty.visibility = View.VISIBLE
                    ivEmpty.visibility = View.VISIBLE
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}