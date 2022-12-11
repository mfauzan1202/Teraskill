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
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.co.mka.teraskill.R
import id.co.mka.teraskill.data.dataclass.UserInfo
import id.co.mka.teraskill.databinding.FragmentSignUpBinding
import id.co.mka.teraskill.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class SignUpFragment : Fragment() {

    private var statusAddPhoto = false
    private lateinit var filePath: Uri

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignUpViewModel by viewModel()
    private var imageFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getImage =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                if (uri != null) {
                    filePath = uri
                    imageFile = uriToFile(filePath, requireContext(), "image")
                    imageFile = saveBitmapToFile(imageFile!!)
                    statusAddPhoto = true
                    Glide.with(this)
                        .load(uri)
                        .apply(RequestOptions.circleCropTransform())
                        .into(binding.ivProfilePicture)
                }
            }

        binding.apply {
            fabAddPhoto.setOnClickListener {
                if (statusAddPhoto) {
                    statusAddPhoto = false
                    fabAddPhoto.setImageResource(R.drawable.ic_add_photo)
                    ivProfilePicture.setImageResource(R.drawable.user_pic)
                } else {
                    getImage.launch("image/*")
                }
            }

            focusChange(tilName, etName)
            focusChange(tilPhoneNumber, etPhoneNumber)
            focusChange(tilEmail, etEmail)
            focusChange(tilPassword, etPassword)
            focusChange(tilVerifyPassword, etVerifyPassword)

            ibBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSignup.setOnClickListener {
                /** Checking error state status on all Edit text **/
                checkError(tilName, etName)
                checkError(tilPhoneNumber, etPhoneNumber)
                checkError(tilEmail, etEmail)
                checkError(tilPassword, etPassword)
                checkError(tilVerifyPassword, etVerifyPassword)


                if (
                    isErrorOrEmpty(tilName, etName) ||
                    isErrorOrEmpty(tilPhoneNumber, etPhoneNumber) ||
                    isErrorOrEmpty(tilEmail, etEmail) ||
                    isErrorOrEmpty(tilPassword, etPassword) ||
                    isErrorOrEmpty(tilVerifyPassword, etVerifyPassword)
                ) {
                    return@setOnClickListener
                } else {
                    showLoading(true, requireContext())
                    uploadData()
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
                    "Tolong masukkan Kata sandi sedikitnya 8 karakter",
                    requireContext()
                )
                editText == etVerifyPassword && editText.text.toString() != etPassword.text.toString() -> setError(
                    textInputLayout,
                    editText,
                    "Kata sandi yang anda masukkan tidak sama",
                    requireContext()
                )
                editText == etEmail && !isEmailValid(editText.text.toString()) -> setError(
                    textInputLayout,
                    editText,
                    "Tolong masukkan alamat email yang benar",
                    requireContext()
                )
                editText == etName && editText.text.toString().length < 3 -> setError(
                    textInputLayout,
                    editText,
                    "Tolong masukkan nama sedikitnya 3 karakter, dan nama tidak boleh mengandung angka/simbol",
                    requireContext()
                )
            }
        }
    }

    private fun uploadData() {
        binding.apply {
            viewModel.signUpUser(
                UserInfo(
                    etName.text.toString(),
                    etPhoneNumber.text.toString(),
                    etEmail.text.toString(),
                    etPassword.text.toString(),
                    etVerifyPassword.text.toString()
                ),
                imageFile
            ).observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {
                        val dialogView = LayoutInflater.from(requireContext())
                            .inflate(R.layout.dialog_register, null)
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                            .setView(dialogView)
                            .show()

                        showLoading(false, requireContext())

                        dialogView.findViewById<Button>(R.id.btn_done).setOnClickListener {
                            dialogBuilder.dismiss()
                            findNavController().navigate(
                                SignUpFragmentDirections.actionSignUpFragmentToVerificationOTPFragment(
                                    etEmail.text.toString()
                                )
                            )
                        }
                    }
                    is Resource.Error -> {
                        showLoading(false, requireContext())
                        Toast.makeText(
                            requireContext(),
                            "Daftar gagal, silahkan coba lagi",
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