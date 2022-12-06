package id.co.mka.teraskill.ui.checkout_class

import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import id.co.mka.teraskill.databinding.FragmentDetailPaymentBinding
import id.co.mka.teraskill.utils.Preferences

class DetailPaymentFragment : Fragment() {

    private var _binding: FragmentDetailPaymentBinding? = null
    private val binding get() = _binding!!
    private val args: DetailPaymentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = Preferences(requireContext())

        binding.apply {
            Glide.with(requireContext())
                .load(args.urlImage)
                .into(itemImage)

            itemName.text = args.className
            tvClassName.text = args.className
            tvUserName.text = preferences.getValues("name")
            tvLearningPathName.text = args.pathName
            tvPriceNominal.text = "Rp.${args.price}"
            tvTransferNominal.text = "Rp.${args.price}"

            btnNext.setOnClickListener {
                findNavController().navigate(
                    DetailPaymentFragmentDirections.actionDetailPaymentFragmentToUploadReceiptFragment(
                        args.urlImage,
                        args.className,
                        args.price,
                        args.classID,
                        args.classUuid
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}