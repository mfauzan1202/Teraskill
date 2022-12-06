package id.co.mka.teraskill.ui.applying.biodata

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.co.mka.teraskill.R
import id.co.mka.teraskill.data.dataclass.ApplyData
import id.co.mka.teraskill.databinding.FragmentBiodataBinding
import id.co.mka.teraskill.utils.*
import java.util.*


class BioDataFragment : Fragment() {
    private val bankList = ArrayList<String>()
    private var _binding: FragmentBiodataBinding? = null
    private val binding get() = _binding!!
    private val MONTHS = arrayOf(
        "Januari",
        "Februari",
        "Maret",
        "April",
        "Mei",
        "Juni",
        "Juli",
        "Agustus",
        "September",
        "Oktober",
        "November",
        "Desember"
    )
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBiodataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Pendaftaran Jadi Mentor"
        setBankName()

        binding.apply {
            focusChange(tilAccountBankName, etAccountBankName)
            focusChange(tilBirthDate, etBirthDate)
            focusChange(tilAddress, etAddress)
            focusChange(tilJob, etJob)
            focusChange(tilAgency, etAgency)
            focusChange(tilBankName, etBankName)
            focusChange(tilAccountNumber, etAccountNumber)

            /** Birthdate field handler */
            etBirthDate.setOnClickListener {
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    R.style.DatePickerDialogTheme,
                    { _, year, month, dayOfMonth ->
                        val monthName = MONTHS[month]
                        etBirthDate.setText("$dayOfMonth ${monthName} $year")
                    },
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                )
                datePickerDialog.show()
            }
            /** Bank field handler */
            etBankName.setOnClickListener {
                showBottomSheetDialog()
            }

            /** Submit button handler */
            btnNext.setOnClickListener {
                checkError(tilAccountBankName, etAccountBankName)
//                checkError(tilBirthDate, etBirthDate)
                checkError(tilAddress, etAddress)
                checkError(tilJob, etJob)
                checkError(tilAgency, etAgency)
//                checkError(tilBankName, etBankName)
                checkError(tilAccountNumber, etAccountNumber)

                if (isErrorOrEmpty(tilAccountBankName, etAccountBankName) ||
                    isErrorOrEmpty(tilBirthDate, etBirthDate) ||
                    isErrorOrEmpty(tilAddress, etAddress) ||
                    isErrorOrEmpty(tilJob, etJob) ||
                    isErrorOrEmpty(tilAgency, etAgency) ||
                    isErrorOrEmpty(tilBankName, etBankName) ||
                    isErrorOrEmpty(tilAccountNumber, etAccountNumber)
                ) {
                    return@setOnClickListener
                } else {
                    sendData()
                }
            }
        }
    }

    private fun sendData() {
        binding.apply {
            val data = ApplyData(
                etAccountBankName.text.toString(),
                etBirthDate.text.toString(),
                etAddress.text.toString(),
                etJob.text.toString(),
                etAgency.text.toString(),
                etBankName.text.toString(),
                etAccountNumber.text.toString(),
            )
            findNavController().navigate(
                BioDataFragmentDirections.actionBiodataFragmentToUploadCVFragment(
                    data
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun focusChange(textInputLayout: TextInputLayout, editText: TextInputEditText) {
        binding.apply {
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
                editText == etAccountBankName && editText.text.toString().length < 3 -> setError(
                    textInputLayout,
                    editText,
                    "Name must be more than 3 characters and must not contain any number or symbol",
                    requireContext()
                )
                editText == etAccountNumber && editText.text.toString().length < 10 -> setError(
                    textInputLayout,
                    editText,
                    "Tolong masukkan nomor rekening yang biasanya terdiri dari 10 angka atau lebih",
                    requireContext()
                )
            }
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_bank, null)
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
        recyclerView = bottomSheetView.findViewById(R.id.rv_bank)
        recyclerView.adapter = BioDataAdapter(bankList) {
            binding.etBankName.setText(it)
            bottomSheetDialog.dismiss()
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setBankName() {
        bankList.clear()
        bankList.addAll(
            listOf(
                "Bank BRI",
                "Bank BNI",
                "Bank BCA",
                "BSI",
                "Bank BTN",
                "Bank Mandiri",
                "Bank CIMB NIAGA",
                "Bank DANAMON",
                "Bank PANIN",
                "Bank MEGA",
                "Bank UOB IDONESIA",
                "Bank MAYBANK INDONESIA",
                "Bank BUMI ARTA",
            )
        )
    }
}