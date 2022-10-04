package id.co.mka.teraskill.ui.auth.signup

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.co.mka.teraskill.*
import id.co.mka.teraskill.databinding.FragmentSignUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpFragment : Fragment() {

    private var statusAddPhoto = false
    private lateinit var filePath: Uri

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getImage =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                if (uri != null) {
                    filePath = uri
                    statusAddPhoto = true
                    Glide.with(this)
                        .load(uri)
                        .apply(RequestOptions.circleCropTransform())
                        .into(binding.ivProfilePicture)
                }
            }

        binding.apply {
            focusChange(tilName, etName)
            focusChange(tilPhoneNumber, etPhoneNumber)
            focusChange(tilEmail, etEmail)
            focusChange(tilPassword, etPassword)
            focusChange(tilVerifyPassword, etVerifyPassword)

            btnSignup.setOnClickListener {
                /** Checking error state status on all Edit text **/
                if (
                    isErrorOrEmpty(tilName, etName) ||
                    isErrorOrEmpty(tilPhoneNumber, etPhoneNumber) ||
                    isErrorOrEmpty(tilEmail, etEmail) ||
                    isErrorOrEmpty(tilPassword, etPassword) ||
                    isErrorOrEmpty(tilVerifyPassword, etVerifyPassword)
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Please fill the field with the right data",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    handleSignUp(
                        UserInfo(
                            etName.text.toString(),
                            etPhoneNumber.text.toString(),
                            etEmail.text.toString(),
                            etPassword.text.toString(),
                            etVerifyPassword.text.toString()
                        )
                    )
                }
            }

            fabAddPhoto.setOnClickListener {
                if (statusAddPhoto) {
                    statusAddPhoto = false
                    fabAddPhoto.setImageResource(R.drawable.ic_add_photo)
                    ivProfilePicture.setImageResource(R.drawable.user_pic)
                } else {
                    getImage.launch("image/*")
                }
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
                    editText == etVerifyPassword && editText.text.toString() != etPassword.text.toString() -> setError(
                        textInputLayout,
                        editText,
                        "Password not match",
                        requireContext()
                    )
                    editText == etEmail && !isEmailValid(editText.text.toString()) -> setError(
                        textInputLayout,
                        editText,
                        "Please enter a valid email address",
                        requireContext()
                    )
                    editText == etName && editText.text.toString().length < 3 -> setError(
                        textInputLayout,
                        editText,
                        "Name must be more than 3 characters and must not contain any number or symbol",
                        requireContext()
                    )
                }
            }
        }
    }

    private fun handleSignUp(userInfo: UserInfo) {
        ApiConfig.getApiService().registerUser(
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