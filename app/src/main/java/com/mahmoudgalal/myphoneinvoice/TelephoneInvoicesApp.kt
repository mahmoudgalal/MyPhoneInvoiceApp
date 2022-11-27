package com.mahmoudgalal.myphoneinvoice

import androidx.multidex.MultiDexApplication
import com.mahmoudgalal.myphoneinvoice.db.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import java.lang.Exception

@HiltAndroidApp
class TelephoneInvoicesApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
    }
}