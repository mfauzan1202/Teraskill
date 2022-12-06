package id.co.mka.teraskill.application

import android.app.Application
import id.co.mka.teraskill.di.dataModule
import id.co.mka.teraskill.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@Application)
            modules(dataModule)
            modules(viewModelModule)
        }
    }
}