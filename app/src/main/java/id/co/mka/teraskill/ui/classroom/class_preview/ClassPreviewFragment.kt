package id.co.mka.teraskill.ui.classroom.class_preview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import id.co.mka.teraskill.R
import id.co.mka.teraskill.data.responses.SingleClassResponse
import id.co.mka.teraskill.databinding.FragmentClassPreviewBinding
import id.co.mka.teraskill.ui.checkout_class.CheckoutClassActivity
import id.co.mka.teraskill.ui.checkout_class.CheckoutClassActivity.Companion.CLASS_ID
import id.co.mka.teraskill.ui.checkout_class.CheckoutClassActivity.Companion.CLASS_NAME
import id.co.mka.teraskill.ui.checkout_class.CheckoutClassActivity.Companion.CLASS_UUID
import id.co.mka.teraskill.ui.checkout_class.CheckoutClassActivity.Companion.PATH_NAME
import id.co.mka.teraskill.ui.checkout_class.CheckoutClassActivity.Companion.PRICE
import id.co.mka.teraskill.ui.checkout_class.CheckoutClassActivity.Companion.URL_IMAGE
import id.co.mka.teraskill.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClassPreviewFragment : Fragment() {

    private var _binding: FragmentClassPreviewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ClassPreviewViewModel by viewModel()
    private val args: ClassPreviewFragmentArgs by navArgs()
    private val adapter: ClassPreviewAdapter by lazy {
        ClassPreviewAdapter {
            findNavController().navigate(
                ClassPreviewFragmentDirections.actionClassPreviewFragmentToClassTheoryFragment(
                    it, args.uuid
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentClassPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            icBack.setOnClickListener {
                activity?.finish()
            }

            viewModel.getDetailClass(args.uuid).observe(viewLifecycleOwner) { data ->
                if (data != null) {
                    checkStatus(data.status)
                    val response = data.response
                    setDetailClass(response)

                    btnJoinClass.setOnClickListener {
                        if (response.type == "Gratis") {
                            joinFreeClass(response.id)
                        } else {
                            val intent = Intent(requireContext(), CheckoutClassActivity::class.java)
                            intent.putExtra(CLASS_ID, response.id.toString())
                            intent.putExtra(CLASS_NAME, response.name)
                            intent.putExtra(PATH_NAME, response.learningPath.name)
                            intent.putExtra(PRICE, response.price.toString())
                            intent.putExtra(URL_IMAGE, response.image)
                            intent.putExtra(CLASS_UUID, response.uuid)

                            startActivity(intent)
                        }
                    }

                    btnSubmitAssigment.setOnClickListener {
                        findNavController().navigate(
                            ClassPreviewFragmentDirections.actionClassPreviewFragmentToClassProjectFragment(
                                response.uuid
                            )
                        )
                    }
                    btnQuiz.setOnClickListener {
                        findNavController().navigate(
                            ClassPreviewFragmentDirections.actionClassPreviewFragmentToClassExamFragment(
                                response.uuid
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setDetailClass(response: SingleClassResponse.Response) {
        binding.apply {
            Glide.with(requireContext())
                .load(response.image)
                .into(ivClassPreview)
            tvClassTitle.text = response.name
            tvTotalModule.text =
                getString(R.string.total_modul, response.jmlMateriText.toString())
            tvTotalVideo.text =
                getString(R.string.total_video, response.jmlMateriVideo.toString())
            tvSpecification.text = response.tools
            response.mentorInfo.let { user ->
                tvUser.text = user.name
                user.mentorJob.let { mentor ->
                    tvJobDivision.text = mentor.job
                }
            }
            tvClassDesc.text = response.about

            adapter.setData(response.moduls)
            setAdapter()
        }
    }

    private fun setAdapter() {
        binding.apply {
            rvListChapter.adapter = adapter
            rvListChapter.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun joinFreeClass(id: Int) {
        viewModel.joinFreeClass(id).observe(viewLifecycleOwner) { data ->
            if (data != null) {
                when (data) {
                    is Resource.Success -> {
                        Toast.makeText(requireContext(), "Berhasil bergabung", Toast.LENGTH_SHORT)
                            .show()
                        binding.btnJoinClass.visibility = View.GONE
                        unlock(1)
                    }
                    else -> {
                        Toast.makeText(requireContext(), "Gagal bergabung", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun unlock(unlockCode: Int) {
        binding.apply {
            btnJoinClass.visibility = View.GONE
            when (unlockCode) {
                1 -> {
                    viewLockListMateri.visibility = View.GONE
                    icLockListMateri.visibility = View.GONE
                }
                2 -> {
                    viewLockFinalExam.visibility = View.GONE
                    icLockFinalExam.visibility = View.GONE
                }
                3 -> {
                    btnClassReview.visibility = View.VISIBLE
                }
                4 -> {
                    viewLockCertificate.visibility = View.GONE
                    icLockCertificate.visibility = View.GONE
                }
            }
        }
    }

    private fun checkStatus(status: SingleClassResponse.Status) {
        binding.apply {
            if (status.pembelian == "sudah dibeli" || status.status_beli == "Lunas") {
                unlock(1)
                if (status.materi == "materi sudah selesai") {
                    unlock(2)
                    if (status.exam_lulus == "lulus") {
                        unlock(3)
                        btnQuiz.backgroundTintList =
                            AppCompatResources.getColorStateList(
                                requireContext(),
                                R.color.primary_color
                            )
                        btnQuiz.isEnabled = false
                        btnQuiz.setTextColor(
                            AppCompatResources.getColorStateList(
                                requireContext(),
                                R.color.white
                            )
                        )
                        if (status.review == "sudah review") {
                            unlock(4)
                        }
                    }
                }
            } else {
                btnJoinClass.visibility = View.VISIBLE
            }
        }
    }
}