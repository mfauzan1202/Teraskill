package id.co.mka.teraskill.ui.checkout_class

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import id.co.mka.teraskill.R
import id.co.mka.teraskill.databinding.ActivityCheckoutClassBinding


class CheckoutClassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutClassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val classID = intent.getStringExtra(CLASS_ID)
        val classUUID = intent.getStringExtra(CLASS_UUID)
        val className = intent.getStringExtra(CLASS_NAME)
        val pathName = intent.getStringExtra(PATH_NAME)
        val price = intent.getStringExtra(PRICE)
        val image = intent.getStringExtra(URL_IMAGE)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.setGraph(
            R.navigation.navigation_checkout,
            DetailPaymentFragmentArgs(
                classID!!,
                className!!,
                pathName!!,
                price!!,
                image!!,
                classUUID!!
            ).toBundle()
        )

    }

    companion object{
        const val CLASS_ID = "class_id"
        const val CLASS_UUID = "class_uuid"
        const val CLASS_NAME = "class_name"
        const val PATH_NAME = "path_name"
        const val PRICE = "price"
        const val URL_IMAGE = "url_image"
    }
}