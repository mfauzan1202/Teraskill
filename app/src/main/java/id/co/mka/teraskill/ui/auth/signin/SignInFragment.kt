package id.co.mka.teraskill.ui.auth.signin

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.co.mka.teraskill.*
import id.co.mka.teraskill.databinding.FragmentSignInBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

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
                    handleSignIn(
                        UserInfo(
                            email = etEmail.text.toString(),
                            password = etPassword.text.toString(),
                        )
                    )
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

    private fun handleSignIn(userInfo: UserInfo) {
        ApiConfig.getApiService().loginUser(
            userInfo
        ).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        val dialogView = LayoutInflater.from(requireContext())
                            .inflate(R.layout.dialog_register, null)
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                            .setView(dialogView)
                            .show()

                        dialogView.findViewById<Button>(R.id.btn_done).setOnClickListener {
                            dialogBuilder.dismiss()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }
}