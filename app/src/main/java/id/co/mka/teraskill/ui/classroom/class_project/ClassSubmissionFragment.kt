package id.co.mka.teraskill.ui.classroom.class_project

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.FragmentClassSubmissionBinding
import id.co.mka.teraskill.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClassSubmissionFragment : Fragment() {

    private var _binding: FragmentClassSubmissionBinding? = null
    private val binding get() = _binding!!
    private val args: ClassSubmissionFragmentArgs by navArgs()
    private val viewModel: ClassProjectViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentClassSubmissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvProjectDesc.text = args.description
            btnSend.setOnClickListener {
                submitLink()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun submitLink() {
        viewModel.uploadProjectLink(args.uuid, binding.etLinkSubmission.text.toString())
            .observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> TODO()
                    is Resource.Loading -> TODO()
                    is Resource.Success -> {
                        val dialogView = LayoutInflater.from(requireContext())
                            .inflate(R.layout.dialog_submit_submission, null)
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                            .setView(dialogView)
                            .show()

                        dialogView.findViewById<Button>(R.id.btn_back).setOnClickListener {
                            dialogBuilder.dismiss()
                            findNavController().popBackStack()
                        }
                    }
                }
            }
    }
}