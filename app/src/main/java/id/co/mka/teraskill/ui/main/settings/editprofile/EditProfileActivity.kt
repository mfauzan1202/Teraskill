package id.co.mka.teraskill.ui.main.settings.editprofile

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.co.mka.teraskill.R
import id.co.mka.teraskill.data.dataclass.UserInfo
import id.co.mka.teraskill.databinding.ActivityEditProfileBinding
import id.co.mka.teraskill.utils.*
import java.io.File
import org.koin.androidx.viewmodel.ext.android.viewModel

class
EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModel()

    private var imageFile: File? = null
    private lateinit var filePath: Uri
    private var statusAddPhoto = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preference = Preferences(this)

        val getImage =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                if (uri != null) {
                    filePath = uri
                    imageFile = uriToFile(filePath, this, "image")
                    statusAddPhoto = true
                    Glide.with(this)
                        .load(uri)
                        .apply(RequestOptions.circleCropTransform())
                        .into(binding.ivProfilePicture)
                    binding.tvEditPhoto.text = DocumentFile.fromSingleUri(this, filePath)!!.name
                }
            }

        binding.apply {
            ibBack.setOnClickListener { finish() }
            Glide.with(this@EditProfileActivity)
                .load(preference.getValues("avatar"))
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfilePicture)

            focusChange(tilFullName, etFullName)
            focusChange(tilPhoneNumber, etPhoneNumber)
            focusChange(tilPassword, etPassword)
            focusChange(tilVerifyPassword, etVerifyPassword)

            tilFullName.editText?.setText(preference.getValues("name"))
            tilPhoneNumber.editText?.setText(preference.getValues("no_hp"))
            tilPassword.editText?.setText(preference.getValues("password"))
            tilVerifyPassword.editText?.setText(preference.getValues("confpassword"))

            btnEditPhoto.setOnClickListener {
                if (statusAddPhoto) {
                    statusAddPhoto = false
                    ivProfilePicture.setImageResource(R.drawable.user_pic)
                } else {
                    getImage.launch("image/*")
                }
            }

            btnSave.setOnClickListener {
                /** Checking error state status on all Edit text **/
                if (
                    isErrorOrEmpty(tilFullName, etFullName) ||
                    isErrorOrEmpty(tilPhoneNumber, etPhoneNumber) ||
                    isErrorOrEmpty(tilPassword, etPassword) ||
                    isErrorOrEmpty(tilVerifyPassword, etVerifyPassword)
                ) {
                    Toast.makeText(
                        this@EditProfileActivity,
                        "Please fill the field with the right data",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (preference.getValues("uuid") != null) {
                        showLoading(true, this@EditProfileActivity)
                        uploadData()
                    } else {
                        showLoading(false, this@EditProfileActivity)
                        Toast.makeText(
                            this@EditProfileActivity,
                            "Ada kesalahan, coba login kembali",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun focusChange(textInputLayout: TextInputLayout, editText: TextInputEditText) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (textInputLayout.error != null) {
                    editText.afterTextChanged {
                        removeError(textInputLayout, editText, this)
                    }
                } else {
                    removeError(textInputLayout, editText, this)
                }
            } else {
                checkError(textInputLayout, editText)
            }
        }
    }

    private fun checkError(textInputLayout: TextInputLayout, editText: TextInputEditText) {
        /** Checking empty state status on all Edit text **/
        binding.apply {
            when {
                editText.text.toString().isEmpty() -> setError(
                    textInputLayout,
                    editText,
                    "Field cannot be empty",
                    this@EditProfileActivity
                )
                editText == etPassword && editText.text.toString().length < 8 -> setError(
                    textInputLayout,
                    editText,
                    "Password must be at least 8 characters",
                    this@EditProfileActivity
                )
                editText == etVerifyPassword && editText.text.toString() != etPassword.text.toString() -> setError(
                    textInputLayout,
                    editText,
                    "Password not match",
                    this@EditProfileActivity
                )
                editText == etFullName && editText.text.toString().length < 3 -> setError(
                    textInputLayout,
                    editText,
                    "Name must be more than 3 characters and must not contain any number or symbol",
                    this@EditProfileActivity
                )
            }
        }
    }

    private fun uploadData() {
        val preference = Preferences(this)
        val uuid = preference.getValues("uuid")

        binding.apply {
            val name = etFullName.text.toString()
            val email = preference.getValues("email")
            val noHp = etPhoneNumber.text.toString()
            val password = etPassword.text.toString()
            val confpassword = etVerifyPassword.text.toString()

            viewModel.updateProfile(
                uuid!!, UserInfo(
                    name,
                    noHp,
                    email!!,
                    password,
                    confpassword,
                )
            ).observe(this@EditProfileActivity) {
                when (it) {
                    is Resource.Success -> {
                        preference.apply {
                            setValues("name", name)
                            setValues("password", password)
                            setValues("no_hp", noHp)
                            setValues("confpassword", confpassword)
                        }
                        if (statusAddPhoto) {
                            uploadPhoto(uuid)
                        } else {
                            showLoading(false, this@EditProfileActivity)
                            Toast.makeText(
                                this@EditProfileActivity,
                                "Update profile success",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }
                    is Resource.Error -> {
                        showLoading(false, this@EditProfileActivity)
                        Toast.makeText(
                            this@EditProfileActivity,
                            "Update profile failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Resource.Loading -> {
                        showLoading(true, this@EditProfileActivity)
                    }
                }
            }
        }
    }

    private fun uploadPhoto(uuid: String) {
        val preference = Preferences(this)

        viewModel.updateProfileAvatar(
            uuid, imageFile
        ).observe(this@EditProfileActivity) {
            when (it) {
                is Resource.Success -> {
                    preference.setValues("avatar", it.data!!.newAvatar)
                    Toast.makeText(
                        this@EditProfileActivity,
                        "Update profile success",
                        Toast.LENGTH_SHORT
                    ).show()
                    showLoading(false, this@EditProfileActivity)
                    finish()
                }
                is Resource.Error -> {
                    showLoading(false, this@EditProfileActivity)
                    Toast.makeText(
                        this@EditProfileActivity,
                        "Update profile failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    showLoading(true, this@EditProfileActivity)
                }
            }
        }
    }
}