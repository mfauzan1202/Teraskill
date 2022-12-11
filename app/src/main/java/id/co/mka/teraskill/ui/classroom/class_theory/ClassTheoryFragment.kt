package id.co.mka.teraskill.ui.classroom.class_theory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.mka.teraskill.R
import id.co.mka.teraskill.data.responses.ModulResponse
import id.co.mka.teraskill.data.responses.SingleClassResponse
import id.co.mka.teraskill.databinding.FragmentClassTheoryBinding
import id.co.mka.teraskill.ui.classroom.class_preview.ClassPreviewViewModel
import id.co.mka.teraskill.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClassTheoryFragment : Fragment() {

    private var _binding: FragmentClassTheoryBinding? = null
    private val binding get() = _binding!!
    private val args: ClassTheoryFragmentArgs by navArgs()
    private val viewModel: ClassPreviewViewModel by viewModel()

    private val classViewModel: ClassSubChapterViewModel by viewModel()
    private lateinit var adapter: ClassTheoryAdapter

    private val listModule = HashMap<Int, SingleClassResponse.Response.ModulsItem>()
    private val listChapter = HashMap<Int, List<ModulResponse>>()
    private var pointerModule: String = ""
    private var pointerChapter: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentClassTheoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            icMenu.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            icMenuOn.setOnClickListener {
                drawerLayout.closeDrawer(GravityCompat.START)
            }

            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            adapter = ClassTheoryAdapter {
                when {
                    it.content != null -> setTextSubject(it.title, it.content)
                    it.link != null -> setVideoUrl(it.title, it.link)
                }
                pointerChapter = it.uuid
                pointerModule = it.modul.uuid
            }
            setAdapter()

            viewModel.getDetailClass(args.uuidClass).observe(viewLifecycleOwner) {
                if (it != null) {
                    prepareDataModule(it.response.moduls)
                    (requireActivity() as AppCompatActivity).supportActionBar?.title =
                        it.response.name

                    for (data in 0 until it.response.moduls.size) {
                        classViewModel.getSubChapter(it.response.moduls[data].uuid)
                            .observe(viewLifecycleOwner) { module ->
                                when (module) {
                                    is Resource.Error -> TODO()
                                    is Resource.Loading -> TODO()
                                    is Resource.Success -> {
                                        if (it.response.moduls[data].uuid == args.uuidModule) {
                                            pointerModule = it.response.moduls[data].uuid
                                            val dataSize = module.data!!.size
                                            for (i in 0 until dataSize) {
                                                val moduleData = module.data[i]
                                                if (moduleData.statusProgress == "unread") {
                                                    pointerChapter = moduleData.uuid
                                                    when {
                                                        moduleData.content != null -> {
                                                            setTextSubject(
                                                                moduleData.title,
                                                                moduleData.content
                                                            )
                                                        }
                                                        moduleData.link != null -> {
                                                            setVideoUrl(
                                                                moduleData.title,
                                                                moduleData.link
                                                            )
                                                        }
                                                    }
                                                    adapter.setIDButtonActive(
                                                        args.uuidModule,
                                                        moduleData.uuid
                                                    )
                                                    break
                                                } else if (i == dataSize - 1 && moduleData.statusProgress == "readed") {
                                                    pointerChapter = moduleData.uuid
                                                    when {
                                                        moduleData.content != null -> {
                                                            setTextSubject(
                                                                moduleData.title,
                                                                moduleData.content
                                                            )
                                                        }
                                                        moduleData.link != null -> {
                                                            setVideoUrl(
                                                                moduleData.title,
                                                                moduleData.link
                                                            )
                                                        }
                                                    }
                                                    adapter.setIDButtonActive(
                                                        args.uuidModule,
                                                        moduleData.uuid
                                                    )
                                                    break
                                                }
                                            }
                                        }
                                        prepareDataChapter(
                                            it.response.moduls[data].urutan,
                                            module.data
                                        )
                                    }
                                }
                            }
                    }
                }
            }

            btnNextChapter.setOnClickListener {
                myloop@ for (i in 1 until listChapter.size + 1) {
                    for (j in 0 until listChapter[i]!!.size) {
                        if (listChapter[i]!![j].modul.uuid == pointerModule) {
                            if (listChapter[i]!![j].uuid == pointerChapter && j < listChapter[i]!!.size - 1) {
                                val thisChapter = listChapter[i]!![j]
                                val nextChapter = listChapter[i]!![j + 1]
                                updateProgress(thisChapter, nextChapter)
                                break@myloop
                            } else if (j == listChapter[i]!!.size - 1 && i < listChapter.size) {
                                val thisChapter = listChapter[i]!![j]
                                val nextChapter = listChapter[i + 1]!![0]
                                pointerModule = nextChapter.modul.uuid
                                pointerChapter = nextChapter.uuid
                                updateProgress(thisChapter, nextChapter)
                                break@myloop
                            } else if (j == listChapter[i]!!.size - 1 && i == listChapter.size) {
                                val thisChapter = listChapter[i]!![j]
                                Toast.makeText(
                                    requireContext(),
                                    "Selamat anda telah menyelesaikan kelas ini",
                                    Toast.LENGTH_SHORT
                                ).show()
                                updateProgress(thisChapter, thisChapter)
                            }
                        }
                    }
                }
            }

        }
    }

    private fun setTextSubject(title: String, content: String) {
        binding.apply {
            childFragmentManager.beginTransaction()
                .remove(ClassTheoryVideoFragment())
                .commit()
            fragmentContainerVideo.visibility = View.GONE
            tvSubjects.visibility = View.VISIBLE
            tvTitleChapter.text = title
            tvSubjects.text = content
        }
    }

    private fun setVideoUrl(title: String, url: String) {

        binding.apply {
            fragmentContainerVideo.visibility = View.VISIBLE

            val fragment = ClassTheoryVideoFragment.newInstance(url)
            childFragmentManager.beginTransaction()
                .add(R.id.fragment_container_video, fragment)
                .commit()

            tvSubjects.visibility = View.GONE
            tvTitleChapter.text = title
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setAdapter() {
        binding.apply {
            rvChapter.adapter = adapter
            rvChapter.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun updateProgress(
        thisChapter: ModulResponse,
        nextChapter: ModulResponse
    ) {
        classViewModel.updateProgress(thisChapter.uuid).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> TODO()
                is Resource.Loading -> TODO()
                is Resource.Success -> {
                    if (it.data!!.kelasUserMateris.status == "unread") {
                        when {
                            nextChapter.content != null -> {
                                setTextSubject(
                                    nextChapter.title,
                                    nextChapter.content
                                )
                            }
                            nextChapter.link != null -> {
                                setVideoUrl(
                                    nextChapter.title,
                                    nextChapter.link
                                )
                            }
                        }
                        adapter.setIDButtonActive(
                            nextChapter.modul.uuid,
                            nextChapter.uuid
                        )
                        pointerChapter = nextChapter.uuid
                    }
                }
            }
        }
    }

    private fun prepareDataModule(dataModule: List<SingleClassResponse.Response.ModulsItem>) {
        listModule.clear()
        for (i in dataModule.indices) {
            listModule[dataModule[i].urutan] = dataModule[i]
        }
        adapter.setDataModule(listModule)
    }

    private fun prepareDataChapter(index: Int, dataChapter: List<ModulResponse>?) {
        //TODO: fix this check if dataChapter is null handling
        if (dataChapter!!.isEmpty()) {
            listChapter[index] =
                listOf(
                    ModulResponse(
                        "",
                        "",
                        1,
                        "Kelazzz",
                        listModule[index - 1]!!.uuid,
                        "",
                        0,
                        1,
                        "BISMILLAHIRRAHMANIRRAHIM",
                        "",
                        modul = ModulResponse.ModuleInfoItem(
                            name = "tes",
                            uuid = listModule[index - 1]!!.uuid
                        ),
                        "read"
                    )
                )
        } else {
            listChapter[index] = dataChapter
        }
        adapter.setDataChapter(listChapter)
    }
}