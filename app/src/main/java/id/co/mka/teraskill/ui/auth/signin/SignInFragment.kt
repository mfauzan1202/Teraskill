package id.co.mka.teraskill.ui.auth.signin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.co.mka.teraskill.*
import id.co.mka.teraskill.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = requireContext().getSharedPreferences("login", Context.MODE_PRIVATE).getString("token", "").toString()
        if (token != "") {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToMainActivity())
        }
        binding.apply {
            focusChange(tilEmail, etEmail)
            focusChange(tilPassword, etPassword)

            btnLogin.setOnClickListener {
                if (isErrorOrEmpty(tilEmail, etEmail) || isErrorOrEmpty(tilPassword, etPassword)) {
                    Toast.makeText(
                        requireContext(),
                        "Please fill the field with the right data",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.loginUser(
                        UserInfo(
                            email = etEmail.text.toString(),
                            password = etPassword.text.toString(),
                        )
                    ).observe(viewLifecycleOwner) {
                        if (it != null) {
                            requireContext().getSharedPreferences("login", Context.MODE_PRIVATE)
                                .edit()
                                .putString("token", it.uuid)
                                .putString("name", it.name)
                                .apply()
                            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToMainActivity())
                        } else {
                            Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }

            spannableString(tvRegister, "Tidak punya akun ? ", "Daftar") {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun focusChange(textInputLayout: TextInputLayout, editText: TextInputEditText) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            binding.apply {
                when {
                    /** Remove error State when focused **/
                    hasFocus -> removeError(textInputLayout, editText, requireContext())
                    /** Checking empty state status on all Edit text **/
                    editText.text.toString().isEmpty() -> setError(
                        textInputLayout,
                        editText,
                        "Field cannot be empty",
                        requireContext()
                    )
                    editText == etPassword && editText.text.toString().length < 8 -> setError(
                        textInputLayout,
                        editText,
                        "Password must be at least 8 characters",
                        requireContext()
                    )
                    editText == etEmail && !isEmailValid(editText.text.toString()) -> setError(
                        textInputLayout,
                        editText,
                        "Please enter a valid email address",
                        requireContext()
                    )
                }
            }
        }
    }
}