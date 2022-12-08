package id.co.mka.teraskill.ui.main.settings.transaction_history

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.ActivityTransactionHistoryBinding
import id.co.mka.teraskill.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class TransactionHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionHistoryBinding
    private lateinit var transactionHistoryAdapter: TransactionHistoryAdapter
    private val viewModel: TransactionHistoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            ibBack.setOnClickListener {
                finish()
            }
            getAllTransaction()
            pickDate(etStartDate)
            pickDate(etEndDate)
        }
    }

    private fun getAllTransaction() {
        binding.apply {
            viewModel.getTransactionHistory().observe(this@TransactionHistoryActivity) {
                when (it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        rvTransactionHistory.visibility = View.VISIBLE
                        tvEmpty.visibility = View.GONE
                        ivEmpty.visibility = View.GONE
                        if (it.data!!.kelasUser.isNotEmpty()) {
                            transactionHistoryAdapter =
                                TransactionHistoryAdapter(it.data.kelasUser) {
                                    startActivity(
                                        Intent(
                                            this@TransactionHistoryActivity,
                                            DetailTransactionActivity::class.java
                                        )
                                    )
                                }
                            rvTransactionHistory.layoutManager =
                                LinearLayoutManager(this@TransactionHistoryActivity)
                            rvTransactionHistory.adapter = transactionHistoryAdapter
                        } else {
                            rvTransactionHistory.visibility = View.GONE
                            tvEmpty.visibility = View.VISIBLE
                            ivEmpty.visibility = View.VISIBLE
                        }
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }

    private fun getTransactionByDate(startDate: String, endDate: String) {
        binding.apply {
            viewModel.getTransactionHistoryByDate(startDate, endDate)
                .observe(this@TransactionHistoryActivity) {
                    when (it) {
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            rvTransactionHistory.visibility = View.VISIBLE
                            tvEmpty.visibility = View.GONE
                            ivEmpty.visibility = View.GONE
                            if (it.data!!.kelasUser.isNotEmpty()) {
                                transactionHistoryAdapter =
                                    TransactionHistoryAdapter(it.data.kelasUser) {
                                        startActivity(
                                            Intent(
                                                this@TransactionHistoryActivity,
                                                DetailTransactionActivity::class.java
                                            )
                                        )
                                    }
                                rvTransactionHistory.layoutManager =
                                    LinearLayoutManager(this@TransactionHistoryActivity)
                                rvTransactionHistory.adapter = transactionHistoryAdapter
                            } else {
                                rvTransactionHistory.visibility = View.GONE
                                tvEmpty.visibility = View.VISIBLE
                                ivEmpty.visibility = View.VISIBLE
                            }
                        }
                        is Resource.Error -> {

                        }
                    }
                }
        }
    }

    private fun pickDate(editText: EditText) {
        editText.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@TransactionHistoryActivity,
                R.style.DatePickerDialogTheme,
                { _, year, month, dayOfMonth ->
                    editText.setText("$year-${month + 1}-$dayOfMonth")
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.etStartDate.text.isNotEmpty() && binding.etEndDate.text.isNotEmpty()) {
                    getTransactionByDate(
                        binding.etStartDate.text.toString(),
                        binding.etEndDate.text.toString()
                    )
                }
            }
        })
    }
}