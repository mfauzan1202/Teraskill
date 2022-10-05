package id.co.mka.teraskill.ui.applying

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import id.co.mka.teraskill.ApiConfig
import id.co.mka.teraskill.ApiResponse
import id.co.mka.teraskill.databinding.FragmentApplyingBinding
import id.co.mka.teraskill.toMultipartBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ApplyingFragment : Fragment() {

    private var _binding: FragmentApplyingBinding? = null
    private val binding get() = _binding!!

    private lateinit var pdfFileLetterStatement: File
    private lateinit var pdfFileCV: File

    private val EXTERNAL_STORAGE_PERMISSION_CODE = 23

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
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            EXTERNAL_STORAGE_PERMISSION_CODE
        )
        val resultLauncher1 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                // Initialize result data
                if (result.resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    val pdfUri = result.data?.data!!
                    // Get the file instance
                    pdfFileLetterStatement = File(pdfUri.path!!).absoluteFile
                }
            }
        val resultLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                // Initialize result data
                if (result.resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    val pdfUri = result.data?.data!!
                    // Get the file instance
                    pdfFileCV = File(pdfUri.path!!).absoluteFile
                }
            }

        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "application/pdf"
        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)

        binding.apply {
            btnUpload.setOnClickListener {
                resultLauncher1.launch(pdfIntent)
            }

            btnUpload2.setOnClickListener {
                resultLauncher2.launch(pdfIntent)
            }

            btnSubmit.setOnClickListener {
                uploadFile(pdfFileLetterStatement, pdfFileCV)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun uploadFile(file1: File, file2: File) {

        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addPart(file1.toMultipartBody("surat_pernyataan"))
            .addPart(file2.toMultipartBody("cv"))
            .build()

        ApiConfig.getApiService().uploadFile(body).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Toast.makeText(requireContext(), "Succes"+ res.msg, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error" + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}