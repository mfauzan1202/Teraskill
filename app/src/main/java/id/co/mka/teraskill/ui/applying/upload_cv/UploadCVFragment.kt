package id.co.mka.teraskill.ui.applying.upload_cv

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.FragmentUploadCvBinding
import id.co.mka.teraskill.utils.showLoading
import id.co.mka.teraskill.utils.spannableString
import id.co.mka.teraskill.utils.uriToFile
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class UploadCVFragment : Fragment() {

    private var _binding: FragmentUploadCvBinding? = null
    private val binding get() = _binding!!

    private var pdfFile: File? = null

    private val viewModel: UploadCVViewModel by viewModel()
    private val args: UploadCVFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUploadCvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Pendaftaran Jadi Mentor"

        binding.apply {

            btnChooseFile.setOnClickListener {
                selectPDF()
            }

            spannableString(
                tvHeading,
                "Mohon dibaca Syarat dan Ketentuan\ngabung jadi mentor",
                13,
                33,
                R.color.secondary_color
            ) {
                val url =
                    "https://docs.google.com/document/d/1DHfWKSlxPtSixyj8cfV04XNEbGWrTlgJ96P2vjC0fRE/edit?usp=drivesdk"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }

            btnSubmit.setOnClickListener {
                val job = etSkill.text.toString().trim()
                showLoading(true, requireActivity())
                when {
                    cbAgreement.isChecked && pdfFile != null -> {
                        viewModel.uploadFile(job, pdfFile!!, args.data)
                            .observe(viewLifecycleOwner) {
                                if (it != null) {
                                    showLoading(false, requireActivity())
                                    findNavController().navigate(UploadCVFragmentDirections.actionUploadCVFragmentToVerificationProcessFragment())
                                } else {
                                    showLoading(false, requireActivity())
                                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                    }
                    cbAgreement.isChecked -> {
                        showLoading(false, requireActivity())
                        Toast.makeText(
                            requireContext(),
                            "Silahkan pilih file untuk diupload terlebih dahulu",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        showLoading(false, requireActivity())
                        Toast.makeText(
                            requireContext(),
                            "Silahkan centang persetujuan terlebih dahulu",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun selectPDF() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        val chooser = Intent.createChooser(intent, "Choose PDF file")

        launcherIntentFile.launch(chooser)
    }

    private val launcherIntentFile = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedFile: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedFile, requireContext(), "pdf")
            pdfFile = myFile

            /** Get file name */
            binding.tvFileName.text = DocumentFile.fromSingleUri(requireContext(), selectedFile)!!.name
        }
    }

}