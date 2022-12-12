package id.co.mka.teraskill.ui.auth.otp

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.FragmentVerificationOtpBinding
import id.co.mka.teraskill.utils.*
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class VerificationOTPFragment : Fragment() {

    private var _binding: FragmentVerificationOtpBinding? = null
    private val binding get() = _binding!!
    private var cdTimer: CountDownTimer? = null

    private val viewModel: VerificationOTPViewModel by viewModel()
    private val args: VerificationOTPFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentVerificationOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //TODO: Make this Timer Can be Cancelled
            startTimer()
            /** Make focus to next view */
            etOtp1.addTextChangedListener(GenericTextWatcher(etOtp1, etOtp2))
            etOtp2.addTextChangedListener(GenericTextWatcher(etOtp2, etOtp3))
            etOtp3.addTextChangedListener(GenericTextWatcher(etOtp3, etOtp4))
            etOtp4.addTextChangedListener(GenericTextWatcher(etOtp4, etOtp5))
            etOtp5.addTextChangedListener(GenericTextWatcher(etOtp5, etOtp6))
            etOtp6.addTextChangedListener(GenericTextWatcher(etOtp6, null))

            /** Make focus to next view after delete */
            etOtp1.setOnKeyListener(GenericKeyEvent(etOtp1, null))
            etOtp2.setOnKeyListener(GenericKeyEvent(etOtp2, etOtp1))
            etOtp3.setOnKeyListener(GenericKeyEvent(etOtp3, etOtp2))
            etOtp4.setOnKeyListener(GenericKeyEvent(etOtp4, etOtp3))
            etOtp5.setOnKeyListener(GenericKeyEvent(etOtp5, etOtp4))
            etOtp6.setOnKeyListener(GenericKeyEvent(etOtp6, etOtp5))

            btnVerif.setOnClickListener {
                showLoading(true, requireContext())
                val otpToken =
                    etOtp1.text.toString() + etOtp2.text.toString() + etOtp3.text.toString() + etOtp4.text.toString() + etOtp5.text.toString() + etOtp6.text.toString()

                if (args.isForgotPass) {
                    verifyEmailForgotPass(otpToken)
                } else {
                    verifyRegister(otpToken)
                }
            }
        }
    }

    private fun verifyEmailForgotPass(otpToken: String) {
        viewModel.sendOTPResetPassword(args.email, otpToken.toInt()).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.apply {
                        btnVerif.isEnabled = false
                        btnVerif.text = "Loading..."
                    }
                }
                is Resource.Success -> {
                    binding.apply {
                        btnVerif.isEnabled = true
                        btnVerif.text = "Verifikasi"
                    }
                    if (it.data != null) {
                        showLoading(false, requireContext())
                        val dialogView = LayoutInflater.from(requireContext())
                            .inflate(R.layout.dialog_verified, null)
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                            .setView(dialogView)
                            .show()
                        dialogView.findViewById<Button>(R.id.btn_done).text = "Lanjutkan"
                        dialogView.findViewById<Button>(R.id.btn_done).setOnClickListener {
                            dialogBuilder.dismiss()
                            showLoading(false, requireContext())
                            findNavController().navigate(
                                VerificationOTPFragmentDirections.actionVerificationOTPFragmentToResetPasswordFragment(
                                    args.email
                                )
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    binding.apply {
                        btnVerif.isEnabled = true
                        btnVerif.text = "Verifikasi"
                        showLoading(false, requireContext())
                    }
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        cancelTimer()
    }

    private fun startTimer() {
        binding.apply {
            cdTimer = object : CountDownTimer(300000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    tvResendOtpTimer.text = resources.getString(
                        R.string.format_otp_timer,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                        )
                    )
                }

                override fun onFinish() {
                    spannableString(tvResendOtpTimer, "Kirim Ulang", 0, 11, R.color.primary_color) {
                        if (args.isForgotPass) {
                            viewModel.resendOTPResetPassword(args.email)
                                .observe(viewLifecycleOwner) {
                                    if (it != null) {
                                        startTimer()
                                    }
                                }
                        } else {
                            viewModel.resendOTP(args.email)
                                .observe(viewLifecycleOwner) {
                                    if (it != null) {
                                        startTimer()
                                    }
                                }
                        }
                    }
                }
            }.start()
        }
    }

    private fun cancelTimer() {
        if (cdTimer != null) {
            cdTimer!!.cancel()
        }
    }

    private fun verifyRegister(otpToken: String) {
        viewModel.sendOTP(args.email, otpToken.toInt()).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    val dialogView = LayoutInflater.from(requireContext())
                        .inflate(R.layout.dialog_verified, null)
                    val dialogBuilder = AlertDialog.Builder(requireContext())
                        .setView(dialogView)
                        .show()
                    dialogView.findViewById<Button>(R.id.btn_done).setOnClickListener {
                        dialogBuilder.dismiss()
                        showLoading(false, requireContext())
                        findNavController().navigate(VerificationOTPFragmentDirections.actionVerificationOTPFragmentToSignInFragment())
                    }
                }
                is Resource.Error -> {
                    showLoading(false, requireContext())
                    when (it.statusCode) {
                        400 -> {
                            binding.apply {
                                dialogBubbleError.tvError.text =
                                    getString(R.string.otp_wrong)
                                dialogBubbleError.root.visibility = View.VISIBLE

                                runBlocking {
                                    dialogBubbleError.root.postDelayed({
                                        dialogBubbleError.root.visibility = View.GONE
                                    }, 3000)
                                }
                            }
                        }
                        404 -> {
                            Toast.makeText(
                                requireContext(),
                                "OTP Expired",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                is Resource.Loading -> TODO()
            }
        }
    }
}