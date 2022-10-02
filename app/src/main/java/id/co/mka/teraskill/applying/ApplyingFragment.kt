package id.co.mka.teraskill.applying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.FragmentApplyingBinding


class ApplyingFragment : Fragment() {

    private var _binding: FragmentApplyingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentApplyingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnUpload.setOnClickListener {
                Toast.makeText(context, "Upload", Toast.LENGTH_SHORT).show()
            }

            btnUpload2.setOnClickListener {
                Toast.makeText(context, "Upload", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}