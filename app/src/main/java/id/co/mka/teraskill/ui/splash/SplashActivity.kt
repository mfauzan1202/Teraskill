package id.co.mka.teraskill.ui.splash

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import id.co.mka.teraskill.R
import id.co.mka.teraskill.data.dataclass.UserInfo
import id.co.mka.teraskill.ui.auth.AuthActivity
import id.co.mka.teraskill.ui.main.MainActivity
import id.co.mka.teraskill.ui.splash.onboarding.OnboardActivity
import id.co.mka.teraskill.utils.Preferences
import id.co.mka.teraskill.utils.Resource

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = SplashViewModel()

        val preferences = Preferences(this)
        val email = preferences.getValues("email")
        val password = preferences.getValues("password")

        if (email!= null && password != null){
            val body = UserInfo(
                email = email,
                password = password,
            )
            viewModel.refreshToken(body).observe(this) {

                when (it){
                    is Resource.Success-> {
                        if (it.data != null){
                            preferences.apply {
                                setValues("uuid", it.data.uuid)
                                setValues("name", it.data.name)
                                setValues("token", it.data.accessToken)
                                setValues("no_hp", it.data.no_hp)
                                setValues("email", it.data.email)
                                setValues("avatar", it.data.avatar)
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "onRefreshToken: ${it.message}")
                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val token = Preferences(this).getValues("token")
            when {
                token != "" -> {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
                Preferences(this).getValues("onboarding") == "1" ->{
                    startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                    finish()
                }
                else -> {
                    startActivity(Intent(this@SplashActivity, OnboardActivity::class.java))
                    finish()
                }
            }
        }, 2000)
    }
}