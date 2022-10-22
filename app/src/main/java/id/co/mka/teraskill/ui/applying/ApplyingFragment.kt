package id.co.mka.teraskill.ui.applying

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.FragmentApplyingBinding
import id.co.mka.teraskill.utils.Preferences
import id.co.mka.teraskill.utils.showLoading
import id.co.mka.teraskill.utils.spannableString
import id.co.mka.teraskill.utils.uriToFile
import java.io.File

class ApplyingFragment : Fragment() {

    private var _binding: FragmentApplyingBinding? = null
    private val binding get() = _binding!!

    private var pdfFileLetterStatement: File? = null
    private var pdfFileCV: File? = null

    private val viewModel: ApplyingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentApplyingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Pendaftaran Jadi Mentor"
        val token = Preferences(requireContext()).getValues("token")

        binding.apply {

            btnChooseLetter.setOnClickListener {
                selectPDF(it)
            }

            btnChooseCv.setOnClickListener {
                selectPDF(it)
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

            spannableString(
                tvChooseLetter,
                "Isilah template Surat Pernyataan (download disini) lalu upload file tersebut dibawah ini",
                33,
                49,
                R.color.primary_color
            ) {
                val url =
                    "https://docs.google.com/document/d/18l-WW-Bo-kPrgHNDPt6QZPgHRbHedG-Kk6ki8hdtCP0/edit?usp=drivesdk"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }

            btnSubmit.setOnClickListener {
                val job = etSkill.text.toString().trim()
                showLoading(true, requireActivity())
                when {
                    cbAgreement.isChecked && pdfFileLetterStatement != null && pdfFileCV != null -> {
                        viewModel.uploadFile(token!!, job, pdfFileLetterStatement!!, pdfFileCV!!)
                            .observe(viewLifecycleOwner) {
                                if (it != null) {
                                    showLoading(false, requireActivity())
                                    findNavController().navigate(ApplyingFragmentDirections.actionApplyingFragmentToVerificationProcessFragment())
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

    private fun selectPDF(view: View) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        val chooser = Intent.createChooser(intent, "Choose PDF file")

        if (view == binding.btnChooseLetter) {
            launcherIntentStatementLetter.launch(chooser)
        } else {
            launcherIntentCV.launch(chooser)
        }
    }

    private val launcherIntentStatementLetter = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedFile: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedFile, requireContext())
            pdfFileLetterStatement = myFile
        }
    }

    private val launcherIntentCV = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedFile: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedFile, requireContext())
            pdfFileCV = myFile
        }
    }

}