package id.co.mka.teraskill.ui.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import id.co.mka.teraskill.R
import id.co.mka.teraskill.ui.main.MainActivity

class FirstSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@FirstSplashActivity, SplashActivity::class.java))
            finish()
        }, 2000)
    }
}