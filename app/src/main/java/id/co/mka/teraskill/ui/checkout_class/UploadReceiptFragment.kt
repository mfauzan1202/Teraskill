package id.co.mka.teraskill.ui.checkout_class

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import id.co.mka.teraskill.databinding.FragmentUploadReceiptBinding
import id.co.mka.teraskill.utils.Preferences
import id.co.mka.teraskill.utils.Resource
import id.co.mka.teraskill.utils.showLoading
import id.co.mka.teraskill.utils.uriToFile
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class UploadReceiptFragment : Fragment() {

    private var _binding: FragmentUploadReceiptBinding? = null
    private val binding get() = _binding!!
    private val args: UploadReceiptFragmentArgs by navArgs()
    private val viewModel: UploadReceiptViewModel by viewModel()

    private var imageFile: File? = null
    private lateinit var filePath: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUploadReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getImage =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                if (uri != null) {
                    filePath = uri
                    imageFile = uriToFile(filePath, requireContext(), "image")
                    binding.tvChooseFile.text =
                        DocumentFile.fromSingleUri(requireContext(), filePath)!!.name
                }
            }

        binding.apply {

            itemName.text = args.className
            Glide.with(requireContext())
                .load(args.urlImage)
                .into(itemImage)
            tvTransferNominal.text = "Rp.${args.price}"

            btnChooseFile.setOnClickListener {
                getImage.launch("image/*")
            }

            btnConfirmation.setOnClickListener {
                if (imageFile == null || binding.etRefNumber.text.toString().isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Pilih bukti pembayaran terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    showLoading(true, requireContext())
                    joinClass()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun joinClass() {
        viewModel.joinClass(args.classID.toInt()).observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Resource.Success -> uploadReceipt()
                    else -> uploadReceipt()
                }
            }
        }
    }

    private fun uploadReceipt() {
        val token = Preferences(requireContext()).getValues("token")
        viewModel.uploadReceipt(
            token!!,
            imageFile!!,
            binding.etRefNumber.text.toString(),
            args.classUuid
        ).observe(viewLifecycleOwner) {
            if (it == "Berhasil mengirim bukti pembayaran") {
                showLoading(false, requireContext())
                requireActivity().finish()
            }else{
                showLoading(false, requireContext())
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}