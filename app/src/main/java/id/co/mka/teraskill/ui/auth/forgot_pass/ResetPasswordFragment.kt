package id.co.mka.teraskill.ui.auth.forgot_pass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.co.mka.teraskill.databinding.FragmentResetPasswordBinding
import id.co.mka.teraskill.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordFragment : Fragment() {

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ResetPasswordViewModel by viewModel()
    private val args: ResetPasswordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            focusChange(tilNewPass, etNewPass)
            focusChange(tilPassConfrim, etPassConfrim)

            btnSave.setOnClickListener {
                checkError(tilNewPass, etNewPass)
                checkError(tilPassConfrim, etPassConfrim)
                if (isErrorOrEmpty(tilNewPass, etNewPass) || isErrorOrEmpty(
                        tilPassConfrim,
                        etPassConfrim
                    )
                ) {
                    return@setOnClickListener
                } else {
                    showLoading(true, requireContext())
                    changePassword(etNewPass.text.toString(), etPassConfrim.text.toString())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun changePassword(password: String, confirmPassword: String) {
        viewModel.changePassword(args.email, password, confirmPassword)
            .observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {
                        showLoading(false, requireContext())
                        findNavController().navigate(
                            ResetPasswordFragmentDirections.actionResetPasswordFragmentToSignInFragment()
                        )
                    }
                    is Resource.Error -> {
                    }
                    else -> {
                        //TODO: handle error
                    }
                }
            }
    }

    private fun focusChange(textInputLayout: TextInputLayout, editText: TextInputEditText) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (textInputLayout.error != null) {
                    editText.afterTextChanged {
                        removeError(textInputLayout, editText, requireContext())
                    }
                } else {
                    removeError(textInputLayout, editText, requireContext())
                }
            } else {
                checkError(textInputLayout, editText)
            }
        }
    }

    private fun checkError(textInputLayout: TextInputLayout, editText: TextInputEditText) {
        binding.apply {
            when {
                /** Checking empty state status on all Edit text **/
                editText.text.toString().isEmpty() -> setError(
                    textInputLayout,
                    editText,
                    "Kolom tidak boleh kosong",
                    requireContext()
                )
                editText == etNewPass && editText.text.toString().length < 8 -> setError(
                    textInputLayout,
                    editText,
                    "Tolong masukkan Password paling sedikit 8 karakter",
                    requireContext()
                )
                editText == etPassConfrim && editText.text.toString().length < 8 -> setError(
                    textInputLayout,
                    editText,
                    "Tolong masukkan Password paling sedikit 8 karakter",
                    requireContext()
                )
            }
        }
    }
}