package id.co.mka.teraskill.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import id.co.mka.teraskill.R
import id.co.mka.teraskill.data.dataclass.MenuButton
import id.co.mka.teraskill.databinding.FragmentHomeBinding
import id.co.mka.teraskill.utils.Preferences
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val buttonData = ArrayList<MenuButton>()

    private lateinit var listMenu: HomeAdapter
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataUser = Preferences(requireContext())
        val nickName = dataUser.getValues("name")!!.split(" ")[0]

        listMenu = HomeAdapter(buttonData)
        getLearningTypeData()
        getClassData()
        setButtonData()

        binding.apply {
            Glide.with(requireContext())
                .load(dataUser.getValues("avatar"))
                .into(ivProfilePicture)

            tvName.text = resources.getString(R.string.hello, nickName)
            rvMenu.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvMenu.adapter = listMenu

            rvLearningType.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvNewClass.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setButtonData() {
        buttonData.addAll(
            listOf(
                MenuButton("Jenis Pembelajaran", R.drawable.ic_healthicons_i_training_class),
                MenuButton("Gabung Jadi Mentor", R.drawable.ic_profile_blue),
                MenuButton("Webinar", R.drawable.ic_video),
                MenuButton("Kompetisi", R.drawable.ic_trophy),
                MenuButton("Dapatkan Pekerjaan", R.drawable.ic_suitcase),
            )
        )
    }

    private fun getLearningTypeData() {
        val adapter = LearningTypeAdapter()
        binding.rvLearningType.adapter = adapter
        viewModel.getLearningPath()
        viewModel.learningPath.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun getClassData() {
        val adapter = NewClassAdapter()
        binding.rvNewClass.adapter = adapter
        viewModel.classInfo.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }
}