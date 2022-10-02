package id.co.mka.teraskill.auth

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.co.mka.teraskill.ApiConfig
import id.co.mka.teraskill.ApiResponse
import id.co.mka.teraskill.R
import id.co.mka.teraskill.UserInfo
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
                if (isErrorOrEmpty(tilName, etName) ||
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
                    hasFocus -> removeError(textInputLayout, editText)
                    /** Checking empty state status on all Edit text **/
                    editText.text.toString().isEmpty() -> setError(
                        textInputLayout,
                        editText,
                        "Field cannot be empty"
                    )
                    editText == etPassword && editText.text.toString().length < 8 -> setError(
                        textInputLayout,
                        editText,
                        "Password must be at least 8 characters"
                    )
                    editText == etVerifyPassword && editText.text.toString() != etPassword.text.toString() -> setError(
                        textInputLayout,
                        editText,
                        "Password not match"
                    )
                    editText == etEmail && !isEmailValid(editText.text.toString()) -> setError(
                        textInputLayout,
                        editText,
                        "Please enter a valid email address"
                    )
                    editText == etName && editText.text.toString().length < 3 -> setError(
                        textInputLayout,
                        editText,
                        "Name must be more than 3 characters and must not contain any number or symbol"
                    )
                }
            }
        }
    }

    private fun setError(
        textInputLayout: TextInputLayout,
        editText: TextInputEditText,
        message: String
    ) {
        textInputLayout.error = message
        editText.background =
            AppCompatResources.getDrawable(requireContext(), R.drawable.bg_textinput_error)
    }

    private fun removeError(textInputLayout: TextInputLayout, editText: TextInputEditText) {
        textInputLayout.error = null
        editText.background =
            AppCompatResources.getDrawable(requireContext(), R.drawable.bg_textinput)
    }

    private fun isEmailValid(email: CharSequence?): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isErrorOrEmpty(
        textInputLayout: TextInputLayout,
        editText: TextInputEditText
    ): Boolean {
        return editText.text.toString().isEmpty() || textInputLayout.error != null
    }

    private fun handleSignUp(userInfo: UserInfo) {
        ApiConfig.getApiService("http://10.0.2.2:5000/").addUser(
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