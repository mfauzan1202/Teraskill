package id.co.mka.teraskill.ui.classroom.class_exam

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.mka.teraskill.R
import id.co.mka.teraskill.data.responses.ExamResponse
import id.co.mka.teraskill.databinding.FragmentClassExamBinding
import id.co.mka.teraskill.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClassExamFragment : Fragment() {

    private var _binding: FragmentClassExamBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ClassExamViewModel by viewModel()
    private val args: ClassExamFragmentArgs by navArgs()
    private var examQuestion = listOf<ExamResponse>()
    private var questionNumber = 0
    private var questionTrueAnswer = 0
    private var isAnswered = false
    private var isAnswerTrue = false
    private val adapter: ClassExamAdapter by lazy {
        ClassExamAdapter { uuid, answered, isTrue ->
            adapter.setQuestionAnswerChoose(uuid)
            isAnswered = answered
            isAnswerTrue = isTrue
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentClassExamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        getClass()
        binding.apply {
            btnNextQuestion.setOnClickListener {
                if (isAnswered && questionNumber < examQuestion.size - 1) {
                    if (isAnswerTrue) {
                        questionTrueAnswer++
                    }
                    setQuestion()
                } else if (isAnswered && questionNumber == examQuestion.size - 1) {
                    if (isAnswerTrue) {
                        questionTrueAnswer++
                    }
                    btnNextQuestion.text = "Selesai"
                    setQuestion()
                }else if (isAnswered && questionNumber == examQuestion.size) {
                    if (isAnswerTrue) {
                        questionTrueAnswer++
                    }
                    submitExam()
                } else {
                    Toast.makeText(requireContext(), "Pilih jawaban terlebih dahulu", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun submitExam() {
        viewModel.submitExam(args.uuid, questionTrueAnswer).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    val dialogView = LayoutInflater.from(requireContext())
                        .inflate(R.layout.dialog_exam_result, null)
                    val dialogBuilder = AlertDialog.Builder(requireContext())
                        .setView(dialogView)
                        .show()

                    val dialogResult = dialogView.findViewById<TextView>(R.id.tv_dialogMessage)
                    dialogResult.text = resources.getString(R.string.tv_dialogMessage_exam, questionTrueAnswer.toString(), examQuestion.size.toString())
                    dialogView.findViewById<Button>(R.id.btn_done).setOnClickListener {
                        dialogBuilder.dismiss()
                        findNavController().popBackStack()
                    }

                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Gagal submit exam", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //TODO
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getClass() {
        viewModel.getExam(args.uuid).observe(viewLifecycleOwner) {
            if (it.data != null) {
                when (it) {
                    is Resource.Success -> {
                        examQuestion = it.data
                        setQuestion()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setQuestion() {
        binding.apply {
            tvQuestionNumber.text = "Soal " + (questionNumber + 1).toString()
            tvQuestion.text =
                (questionNumber + 1).toString() + ". " + examQuestion[questionNumber].soal
            adapter.setQuestionAnswer(examQuestion[questionNumber].optionExams)
        }
        questionNumber += 1
        isAnswered = false
    }

    private fun setAdapter() {
        binding.apply {
            rvAnswer.adapter = adapter
            rvAnswer.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}