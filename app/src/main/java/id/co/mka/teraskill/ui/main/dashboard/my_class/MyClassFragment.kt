package id.co.mka.teraskill.ui.main.dashboard.my_class

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.mka.teraskill.R
import id.co.mka.teraskill.data.responses.MyClassResponse
import id.co.mka.teraskill.databinding.FragmentMyClassBinding
import id.co.mka.teraskill.utils.Preferences
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyClassFragment : Fragment() {

    private var _binding: FragmentMyClassBinding? = null
    private val binding get() = _binding!!
    private val filteredList = ArrayList<MyClassResponse>()

    private lateinit var listAdapter: MyClassAdapter
    private val viewModel: MyClassViewModel by viewModel()
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyClassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        token = Preferences(requireContext()).getValues("token")!!
        getAllClass()

        binding.apply {
            btnAllClass.setOnClickListener {
                getAllClass()
                changeButtonState(btnAllClass)
            }
            btnFreeClass.setOnClickListener {
                getFreeClass()
                changeButtonState(btnFreeClass)
            }
            btnPaidClass.setOnClickListener {
                getPaidClass()
                changeButtonState(btnPaidClass)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getAllClass() {
        binding.apply {
            viewModel.getUserClass().observe(viewLifecycleOwner) {
                if (it != null) {
                    filteredList.clear()
                    for (i in it.indices) {
                        if (it[i].isPaid) {
                            filteredList.add(it[i])
                        }
                    }
                    listAdapter = MyClassAdapter(filteredList)


                    rvMyClass.apply {
                        adapter = listAdapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                } else {
                    //TODO: Handle Error
                }
            }
        }
    }

    private fun getFreeClass() {
        binding.apply {
            viewModel.getUserClass().observe(viewLifecycleOwner) {
                if (it != null) {
                    filteredList.clear()
                    for (i in it.indices) {
                        if (it[i].kelasTeraskill.type == "Gratis") {
                            filteredList.add(it[i])
                        }
                    }
                    listAdapter = MyClassAdapter(filteredList)


                    rvMyClass.apply {
                        adapter = listAdapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                } else {
                    //TODO: Handle Error
                }
            }
        }
    }

    private fun getPaidClass() {
        binding.apply {
            viewModel.getUserClass().observe(viewLifecycleOwner) {
                if (it != null) {
                    filteredList.clear()
                    for (i in it.indices) {
                        if (it[i].kelasTeraskill.type == "Berbayar") {
                            filteredList.add(it[i])
                        }
                    }
                    listAdapter = MyClassAdapter(filteredList)


                    rvMyClass.apply {
                        adapter = listAdapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                } else {
                    //TODO: Handle Error
                }
            }
        }
    }

    private fun changeButtonState(view: View) {
        binding.apply {
            btnAllClass.background =
                ResourcesCompat.getDrawable(resources, R.drawable.button_outlined_blue, null)
            btnAllClass.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            btnFreeClass.background =
                ResourcesCompat.getDrawable(resources, R.drawable.button_outlined_blue, null)
            btnFreeClass.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            btnPaidClass.background =
                ResourcesCompat.getDrawable(resources, R.drawable.button_outlined_blue, null)
            btnPaidClass.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            when (view) {
                btnAllClass -> {
                    btnAllClass.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.button_blue, null)
                    btnAllClass.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.white,
                            null
                        )
                    )
                }
                btnFreeClass -> {
                    btnFreeClass.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.button_blue, null)
                    btnFreeClass.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.white,
                            null
                        )
                    )
                }
                btnPaidClass -> {
                    btnPaidClass.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.button_blue, null)
                    btnPaidClass.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.white,
                            null
                        )
                    )
                }
            }
        }
    }
}