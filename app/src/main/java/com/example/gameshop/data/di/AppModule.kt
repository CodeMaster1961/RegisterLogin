package com.example.gameshop.data.di

import android.content.Context
import android.preference.PreferenceManager
import com.example.gameshop.data.ApiService
import com.example.gameshop.data.repository.GameRepository
import com.example.gameshop.data.repository.UserRepository
import com.example.gameshop.data.repositoryImplementation.GameRepositoryImplementation
import com.example.gameshop.data.repositoryImplementation.UserRepositoryImplementation
import com.example.gameshop.ui.screens.authenticated.AuthenticatedViewModel
import com.example.gameshop.ui.screens.login.HomeViewModel
import com.example.gameshop.ui.screens.profile.ProfileViewModel
import com.example.gameshop.ui.screens.register.RegisterViewModel
import com.example.gameshop.util.Constants.BASE_URL
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        retrofitInstance(getToken(androidContext())).create(ApiService::class.java)
    }

    single<UserRepository> {
        UserRepositoryImplementation(get())
    }

    single<GameRepository> {
        GameRepositoryImplementation(get())
    }

    viewModel<RegisterViewModel> {
        RegisterViewModel(get())
    }

    viewModel<HomeViewModel> {
        HomeViewModel(get(), androidContext())
    }

    viewModel<AuthenticatedViewModel> {
        AuthenticatedViewModel(get())
    }

    viewModel<ProfileViewModel> {
        ProfileViewModel(get(), androidContext())
    }
}

private fun retrofitInstance(token: String?): Retrofit {
    val okHttpClient = OkHttpClient.Builder().apply {
        if (token != null) {
            addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
        }
    }
        .build()
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

 fun getToken(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
    return sharedPreferences.getString("session-id", null)
}