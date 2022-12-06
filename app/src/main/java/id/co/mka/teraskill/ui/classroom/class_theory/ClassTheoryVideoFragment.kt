package id.co.mka.teraskill.ui.classroom.class_theory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import id.co.mka.teraskill.databinding.FragmentClassTheoryVideoBinding


class ClassTheoryVideoFragment : Fragment() {

    private var _binding: FragmentClassTheoryVideoBinding? = null
    private val binding get() = _binding!!
    private var videoId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentClassTheoryVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("videoUrl")?.let {
            val urlToLoad = it.split("embed/".toRegex()).toTypedArray()
            videoId = urlToLoad[1]
        }
        binding.apply {

            lifecycle.addObserver(youtubePlayerView)
            val listener = object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val defaultPlayerUiController =
                        DefaultPlayerUiController(youtubePlayerView, youTubePlayer)
                    youtubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            }

            val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()
            youtubePlayerView.enableAutomaticInitialization = false
            youtubePlayerView.initialize(listener, options)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(url: String) = ClassTheoryVideoFragment().apply {
            arguments = Bundle().apply {
                putString("videoUrl", url)
            }
        }
    }

}