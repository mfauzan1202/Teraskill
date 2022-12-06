package id.co.mka.teraskill.ui.classroom.class_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.mka.teraskill.databinding.FragmentClassProjectBinding
import id.co.mka.teraskill.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClassProjectFragment : Fragment() {

    private var _binding: FragmentClassProjectBinding? = null
    private val binding get() = _binding!!
    private val adapter: ClassProjectAdapter by lazy {
        ClassProjectAdapter { uuid, description ->
            findNavController().navigate(
                ClassProjectFragmentDirections.actionClassProjectFragmentToClassSubmissionFragment(
                    uuid, description
                )
            )
        }
    }
    private val viewModel: ClassProjectViewModel by viewModel()
    private val args: ClassProjectFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentClassProjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        getClassProject()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getClassProject() {
        viewModel.getProject(args.uuid).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    //TODO: Show Loading
                }
                is Resource.Success -> {
                    if (it.data != null) {
                        adapter.setData(it.data)
                    }
                }
                is Resource.Error -> {
                    //TODO: Handle this error
                }
            }
        }
    }

    private fun setAdapter() {
        binding.apply {
            rvSubmission.adapter = adapter
            rvSubmission.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}