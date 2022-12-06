package id.co.mka.teraskill.ui.classroom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.ActivityClassLearningBinding
import id.co.mka.teraskill.ui.classroom.class_preview.ClassPreviewFragmentArgs

class ClassLearningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClassLearningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassLearningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uuid = intent.getStringExtra(EXTRA_ID)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.setGraph(
            R.navigation.navigation_class_learning,
            ClassPreviewFragmentArgs(
                uuid!!
            ).toBundle()
        )

    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}