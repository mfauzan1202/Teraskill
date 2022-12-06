package id.co.mka.teraskill.ui.auth.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.co.mka.teraskill.R
import id.co.mka.teraskill.data.dataclass.UserInfo
import id.co.mka.teraskill.databinding.FragmentSignInBinding
import id.co.mka.teraskill.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignInViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = Preferences(requireContext())
        preferences.setValues("onboarding", "1")

        binding.apply {
            focusChange(tilEmail, etEmail)
            focusChange(tilPassword, etPassword)

            btnLogin.setOnClickListener {
                checkError(tilEmail, etEmail)
                checkError(tilPassword, etPassword)
                if (isErrorOrEmpty(tilEmail, etEmail) || isErrorOrEmpty(tilPassword, etPassword)) {
                    return@setOnClickListener
                } else {
                    showLoading(true, requireContext())
                    uploadData()
                }
            }

            spannableString(tvRegister, "Tidak punya akun? Daftar", 18, 24, R.color.primary_color) {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
            }

            tvForgotPassword.setOnClickListener {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToForgotPasswordFragment())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
                editText == etPassword && editText.text.toString().length < 8 -> setError(
                    textInputLayout,
                    editText,
                    "Tolong masukkan Password paling sedikit 8 karakter",
                    requireContext()
                )
                editText == etEmail && !isEmailValid(editText.text.toString()) -> setError(
                    textInputLayout,
                    editText,
                    "Tolong masukkan email yang valid",
                    requireContext()
                )
            }
        }
    }

    private fun uploadData() {
        val preferences = Preferences(requireContext())
        binding.apply {
            viewModel.loginUser(
                UserInfo(
                    email = etEmail.text.toString(),
                    password = etPassword.text.toString(),
                )
            ).observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {
                        showLoading(false, requireContext())
                        if (it.data != null) {
                            preferences.apply {
                                setValues("uuid", it.data.uuid)
                                setValues("name", it.data.name)
                                setValues("token", it.data.accessToken)
                                setValues("password", etPassword.text.toString())
                                setValues("no_hp", it.data.no_hp)
                                setValues("confpassword", etPassword.text.toString())
                                setValues("email", it.data.email)
                                setValues("avatar", it.data.avatar)
                            }
                            showLoading(false, requireContext())
                            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToMainActivity())
                            activity?.finish()
                        }
                    }
                    is Resource.Error -> {
                        showLoading(false, requireContext())
                        Toast.makeText(
                            requireContext(),
                            "Email atau Password salah",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Resource.Loading -> {
                        showLoading(true, requireContext())
                    }
                }
            }
        }
    }

}