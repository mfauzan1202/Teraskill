package id.co.mka.teraskill.ui.applying

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.FragmentTermsAndConditionBinding
import id.co.mka.teraskill.utils.NumberIndentSpan
import id.co.mka.teraskill.utils.Resource
import id.co.mka.teraskill.utils.spannableString
import org.koin.androidx.viewmodel.ext.android.viewModel

class TermsAndConditionFragment : Fragment() {

    private var _binding: FragmentTermsAndConditionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TermsAndConditionViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTermsAndConditionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            val list = arrayListOf(
                "Anda setuju untuk mengisi semua informasi pribadi seakurat mungkin dan tidak akan berusaha mengelabui pengguna layanan Teraskill.",
                "Anda bertanggung jawab penuh atas semua materi yang Anda sediakan melalui Teraskill.",
                "Anda setuju untuk mengisi kualifikasi dan data lain seakurat dan selengkap mungkin.",
                "Anda setuju untuk dapat menunjukkan sertifikat kualifikasi asli Anda kepada pengguna layanan Teraskill.",
                "Teraskill berhak untuk meminta salinan sertifikat asli Anda untuk verifikasi.",
                "Teraskill tidak punya hak memberikan komisi kepada pihak mentor apabila pengguna layanan belum membayar dan memasuki kelas.",
                "Pembayaran Mentor akan diberikan sejak tanggal masuknya sebagai mentor di Teraskil.",
                "Jika dalam 1 bulan terdapat penghasilan mentor maka biaya yang sudah ditampung dikirim oleh admin.Jika dalam 1 bulan tidak terdapat penghasilan mentor maka akan kembali menunggu pembelian kelas dari pelanggan .",
                "Apabila Anda tidak menerima pembayaran dan bukti pembayaran dalam waktu 2 x24 jam maka segera hubungi pihak Teraskill ."
            )

            val content = SpannableStringBuilder()
            var number = 1
            for (text in list) {
                val contentStart = content.length
                content.appendLine(text)
                content.setSpan(NumberIndentSpan(15, 30, number), contentStart, content.length, 0)
                number++
            }
            tvTermsAndConditions.text = content

            spannableString(
                tvTermsAndConditions2,
                "Lanjutkan baca syarat dan ketentuan gabung jadi mentor disini",
                55,
                61,
                R.color.primary_color
            ) {
                val url =
                    "https://docs.google.com/document/d/1DHfWKSlxPtSixyj8cfV04XNEbGWrTlgJ96P2vjC0fRE/edit?usp=drivesdk"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }

            btnNext.setOnClickListener {
                findNavController().navigate(TermsAndConditionFragmentDirections.actionTermsAndConditionFragmentToBiodataFragment())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getStatusApply() {
        viewModel.getTermsAndCondition().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    if (it.data != null) {
                        it.data[0].apply {
                            if (this.statusPendaftaran == "Data sedang diproses") {
                                findNavController().navigate(TermsAndConditionFragmentDirections.actionTermsAndConditionFragmentToVerificationProcessFragment())
                            } else if (this.statusPendaftaran == "Pendaftaran diterima") {
                                findNavController().navigate(TermsAndConditionFragmentDirections.actionTermsAndConditionFragmentToVerifiedFragment())
                            }
                        }
                    }
                }
                is Resource.Error -> {
                }
            }
        }
    }
}