package id.co.mka.teraskill.ui.main.dashboard.portfolio.add_portfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.co.mka.teraskill.databinding.FragmentAddPortfolioFirstBinding
import id.co.mka.teraskill.utils.afterTextChanged


class AddPortfolioFirstFragment : Fragment() {
    private var _binding: FragmentAddPortfolioFirstBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPortfolioFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnNext.setOnClickListener {
                checkError(etProjectDescription)
                checkError(etSelfDescription)
                focusChange(etProjectDescription)
                focusChange(etSelfDescription)

                if (etProjectDescription.error == null && etSelfDescription.error == null) {
                    val selfDesc = etSelfDescription.text.toString()
                    val projectDesc = etProjectDescription.text.toString()
                    findNavController().navigate(
                        AddPortfolioFirstFragmentDirections.actionAddPortfolioFirstFragmentToAddPortfolioSecondFragment(
                            selfDesc,
                            projectDesc
                        )
                    )
                }
            }
        }
    }

    private fun focusChange(editText: EditText) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (editText.error != null) {
                    editText.afterTextChanged {
                        editText.error = null
                    }
                } else {
                    editText.error = null
                }
            }
        }
    }

    private fun checkError(editText: EditText) {
        binding.apply {
            if (editText.text.toString().isEmpty()) {
                editText.error = "Field tidak boleh kosong"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}