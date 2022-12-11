package id.co.mka.teraskill.ui.main.dashboard.portfolio.add_portfolio

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.co.mka.teraskill.databinding.FragmentAddPortfolioSecondBinding
import id.co.mka.teraskill.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class AddPortfolioSecondFragment : Fragment() {

    private var _binding: FragmentAddPortfolioSecondBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddPortfolioViewModel by viewModel()
    private val args: AddPortfolioSecondFragmentArgs by navArgs()
    private var statusAddPhoto = false
    private lateinit var filePath: Uri
    private var imageFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddPortfolioSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getImage =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                if (uri != null) {
                    filePath = uri
                    imageFile = uriToFile(filePath, requireContext(), "image")
                    statusAddPhoto = true
                    binding.tvImageName.text =
                        DocumentFile.fromSingleUri(requireContext(), filePath)!!.name
                }
            }

        binding.apply {
            btnChooseImage.setOnClickListener {
                getImage.launch("image/*")
            }

            uploadPortfolio.setOnClickListener {
                focusChange(tilProjectTitle, etProjectTitle)
                focusChange(tilProjectRole, etProjectRole)
                focusChange(tilProjectUrl, etProjectUrl)

                if (
                    isErrorOrEmpty(tilProjectTitle, etProjectTitle) ||
                    isErrorOrEmpty(tilProjectRole, etProjectRole) ||
                    isErrorOrEmpty(tilProjectUrl, etProjectUrl)
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Silahkan lengkapi form terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else if (imageFile == null) {
                    Toast.makeText(
                        requireContext(),
                        "Silahkan pilih gambar terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else {
                    showLoading(true, requireContext())
                    addPortfolio()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun focusChange(textInputLayout: TextInputLayout, editText: TextInputEditText) {

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (textInputLayout.error != null) {
                    editText.afterTextChanged {
                        removeError(textInputLayout, editText, requireContext())
                    }
                } else {
                    removeError(textInputLayout, editText, requireContext())
                }
            }
        }
    }

    private fun addPortfolio() {
        binding.apply {
            viewModel.addPortfolio(
                etProjectTitle.text.toString(),
                etProjectRole.text.toString(),
                args.selfDesc,
                args.projectDesc,
                etProjectUrl.text.toString(),
                imageFile!!
            ).observe(viewLifecycleOwner) {
                when(it) {
                    is Resource.Success -> {
                        showLoading(false, requireContext())
                        Toast.makeText(
                            requireContext(),
                            "Portfolio berhasil ditambahkan",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(AddPortfolioSecondFragmentDirections.actionAddPortfolioSecondFragmentToListPortfolioFragment())
                    }
                    is Resource.Error -> {
                        showLoading(false, requireContext())
                        Toast.makeText(
                            requireContext(),
                            "Portfolio gagal ditambahkan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        showLoading(true, requireContext())
                    }
                }
            }
        }

    }
}