package id.co.mka.teraskill.ui.classroom.class_certificate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.co.mka.teraskill.databinding.FragmentCertificateBinding
import id.co.mka.teraskill.utils.showLoading
import org.koin.androidx.viewmodel.ext.android.viewModel

class CertificateFragment : Fragment() {

    private var _binding: FragmentCertificateBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CertificateViewModel

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCertificatePDF() {
        viewModel = ViewModelProvider(this, object: ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CertificateViewModel(fileDir = requireActivity().filesDir) as T
            }
        }).get(CertificateViewModel::class.java)

        viewModel.isFileReadyObserver.observe(viewLifecycleOwner) {
//            showLoading(true, requireContext())

            if (!it) {
                Toast.makeText(requireContext(), "Failed to download PDF", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(requireContext(), "PDF Downloaded successfully", Toast.LENGTH_LONG)
                    .show()
                try {
                    binding.pdfView.fromUri(
                        FileProvider.getUriForFile(
                            requireContext(),
                            "id.co.mka.teraskill.fileprovider",
                            viewModel.getPdfFileUri()
                        )
                    ).load()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Failed to download PDF", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
            viewModel.downloadPdfFile("https://doc-0o-a8-docs.googleusercontent.com/docs/securesc/dghafe0i69akiodt2gljij8k86qm8sg3/qjp02no2dm74408e8bmb9683oc73clrf/1670640600000/09623727501212050953/09623727501212050953/1t8avtvLPdDSnxTBA5GvZXTzGdEY7fstc?e=download&ax=AEKYgySdX9HUZNgIiqzuvdJvpKcgPMYWSVdN6BgtA6DUqRPSvo2IxKrr1HDo-fIuqC9YcMLgMR1sjZkip4y77wjPMqpGbc2xBD2NZbkugawgI6GdthppNDJ4uTTD5aIWz-hipBProZZiHbh2uKz-kqshTiwYUR1A93CuaPks0d0K5v7nNGAZI834oezQ2-jC9zy0xjEM0hnlWOk4PnUzptpGqpyPzLuoiLbX-UOh4XxbHUdCnCRVO0LXNtUmGh997s-6qCIw6e-P0puNEve2O4irNUx4j1QgEKFtEA3pGn1OetLjFqUuY_0wlEw5z2pPH_xE3abMQJXvl2hQpb4_ABafP5yWPyFOGVsscG04IYld-N8lYtBB-9KsX3Ck5XXlR02czXPrjs4lw8e4va9heIldk2_3uv8CnFgf3I_1agCq6fTRSXms0_XwZBSoZQ4wdsLcgCVnAC73Z0qtdbwS54APZiGBRuPhGMc7guhWp8uFs3Rh6V21blQaconp8X0skk-SOhS-i3I6rIfl2jadLZSnhZjnvS_CAvr9oDWFvL3Falfk7VKxX8lY10fBVWZuGblroeOARDk17icZOs95G_BRwXcnCS_GgkhwVaKqiz2kUrXLOCWTWrdaVjitaOhdtyMrja0SMsom-EMOIMWWM_zyYUd6OJiV0gA3YTls3JgjLo45aHYgakMAibMTzHp8KjtK__R7uPssrFWg2WxC-7UxEWCxi7kWOaQXrlB8zNFH_YTtbGC5wRoDwrbOfrmFhK-iIrPHLI3m69tWzj9jtrqzPX8HdMIOQT2hE0Csu1-Cc81rNOieLUTIsWfIe5YwDGEKCwe2KMkVOwXuymyRRfgT6rKSeOC3WZaGTv6nL_XFFsGLiZRAly708ovJpycPsdI_adXAeqYf06MQwdHXYcCfOo0l6SlALQTo9YtwRbWibjK-XZYjXoGFk-YihyBRyWIBkxAMPwNKcgVLJgqHxMQvP5QSqPGtyUW6f-vkP69rqEm4i4dfPQK2B8DknUplpmfCO4EyiW2nYgPOQx62Fr_y&uuid=4245b561-0552-4779-9af7-d6f3edd6aabc&authuser=0&nonce=sdmf88rn4gock&user=09623727501212050953&hash=ge76v8aoc282gut87cl40lq91irccjmc")
        }
    }