package com.mahmoudgalal.myphoneinvoice

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Environment
import android.util.Log
import java.io.File
import java.lang.StringBuilder
import java.util.*

object Utils {
    const val BASE_URL = "https://billing.te.eg/api/Account/"
    const val TOKEN_LENGTH = 32
    const val USER_ID_KEY = "MyPhoneInvoice_USER_ID_KEY"
    private const val SHARED_PREF_FILE_NAME = "MyPhoneInvoice_data"
    private val TAG = Utils::class.java.simpleName

    @JvmStatic
    fun generateRandomToken(): String {
        val sb = StringBuilder()
        sb.append("token=")
        val random = Random()
        for (i in 0 until TOKEN_LENGTH) {
            val ascii = 'A'.code + random.nextInt(6)
            sb.append(ascii.toChar())
        }
        Log.d(TAG,"Generated token:$sb")
        return sb.toString()
    }

    @JvmStatic
    fun getUserID(context: Context): String? {
        return context.getSharedPreferences(SHARED_PREF_FILE_NAME, 0).getString(USER_ID_KEY, null)
    }

    fun clearSharedPref(context: Context) {
        context.getSharedPreferences(SHARED_PREF_FILE_NAME, 0).edit()
            .clear().commit()
    }

    fun generateRandomUserId(): String {
        val uuid = UUID.randomUUID().toString()
        return uuid + "_" + Build.DEVICE + "_" + Build.BRAND + "_" +
                Build.VERSION.CODENAME + "_" + Build.VERSION.RELEASE.replace('.', '_')
    }

    fun saveUserID(id: String?, context: Context) {
        context.getSharedPreferences(SHARED_PREF_FILE_NAME, 0).edit().putString(USER_ID_KEY, id)
            .apply()
    }

    @JvmStatic
    fun isInternetConnectionExist(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting
    }

    /* Checks if external storage is available for read and write */
    val isExternalStorageWritable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
        }

    @JvmStatic
    fun getPublicAppDocsDir(appDir: String?): File {
        // Get the directory for the user's public pictures directory.
        val file = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS
            ), appDir)
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created")
        }
        return file
    }
}