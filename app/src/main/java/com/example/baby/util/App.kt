package com.example.baby.util

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.jakewharton.threetenabp.AndroidThreeTen


// 전역 context - Application 클래스를 상속받는 별도의 클래스 생성
// application context: application 라이프 사이클에 귀속되어 어떤 context보다 오래 유지됨

class App : Application() {
    companion object {
        lateinit var instance: App
            private set

        fun context(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        instance = this
        FirebaseApp.initializeApp(context())
    }
}