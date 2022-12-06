package id.co.mka.teraskill.di

import id.co.mka.teraskill.data.repository.ClassRepository
import id.co.mka.teraskill.data.services.ApiConfig
import id.co.mka.teraskill.utils.Preferences
import org.koin.dsl.module

val dataModule = module {
    single { Preferences(get()).getValues("token") }
    single { ApiConfig.getApiService(get()) }
    single { ClassRepository(get()) }
}