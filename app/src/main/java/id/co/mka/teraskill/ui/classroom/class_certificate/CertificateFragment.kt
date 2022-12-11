package id.co.mka.teraskill.ui.classroom.class_certificate

import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import id.co.mka.teraskill.databinding.FragmentCertificateBinding
import id.co.mka.teraskill.utils.Preferences
import id.co.mka.teraskill.utils.showLoading


class CertificateFragment : Fragment() {

    private var _binding: FragmentCertificateBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CertificateViewModel
    private val args: CertificateFragmentArgs by navArgs()
    private var url: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCertificateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCertificatePDF()
        showLoading(true, requireContext())
        binding.btnDownload.setOnClickListener {
            //check permission
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                //permission granted
                startDownloading(url)
            } else {
                //request permission best practice
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    REQUIRED_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCertificatePDF() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CertificateViewModel(fileDir = requireActivity().filesDir) as T
            }
        })[CertificateViewModel::class.java]

        val token = Preferences(requireContext()).getValues("token").toString()
        viewModel.getLinkPdf(token, args.uuid).observe(viewLifecycleOwner) {
            if (it != null) {
                url = it.sertifikat
            }
        }

        viewModel.isFileReadyObserver.observe(viewLifecycleOwner) {

            if (!it) {
                Toast.makeText(requireContext(), "Gagal Menampilkan Sertifikat", Toast.LENGTH_LONG)
                    .show()
            } else {
                try {
                    showLoading(false, requireContext())
                    binding.pdfView.fromUri(
                        FileProvider.getUriForFile(
                            requireContext(),
                            "id.co.mka.teraskill.fileprovider",
                            viewModel.getPdfFileUri()
                        )
                    ).load()
                } catch (e: Exception) {
                    showLoading(false, requireContext())
                    Toast.makeText(
                        requireContext(),
                        "Gagal Menampilkan Sertifikat\n$e",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }
    }

    private fun startDownloading(url: String) {
        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("Mengunduh")
        request.setDescription("Sedang mengunduh sertifikat")

        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        val name = Preferences(requireContext()).getValues("name").toString()
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Sertifikat_${name}_${System.currentTimeMillis()}.pdf")

        val manager =
            requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}