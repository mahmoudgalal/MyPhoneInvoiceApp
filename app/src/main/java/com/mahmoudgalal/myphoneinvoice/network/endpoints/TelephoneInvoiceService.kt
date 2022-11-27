package com.mahmoudgalal.myphoneinvoice.network.endpoints

import com.mahmoudgalal.myphoneinvoice.Utils
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.mahmoudgalal.myphoneinvoice.network.model.ServiceResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.Header
import java.lang.Exception
import java.lang.RuntimeException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

interface TelephoneInvoiceService {
    @FormUrlEncoded
    @POST("Inquiry")
    suspend fun getTelephoneInvoices(
        @Header("Cookie") tokenCookie: String,
        @Field("InquiryBy") inquiryType: String,
        @Field("AreaCode") areaCode: String,
        @Field("PhoneNumber") phoneNumber: String
    ): ServiceResponse

    companion object{
        private fun getClient() = try {
            // Install the all-trusting trust manager
            // Create an ssl socket factory with our all-trusting manager
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) { }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {  }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { hostname, session -> true }
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        private var sInstance: TelephoneInvoiceService? = null
        @JvmStatic
        val instance: TelephoneInvoiceService
            get() {
                if (sInstance == null) {
                    synchronized(TelephoneInvoiceService::class.java) {
                        if (sInstance == null) {
                            sInstance = Retrofit.Builder()
                                .baseUrl(Utils.BASE_URL)
                                .client(getClient().build())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build().create(TelephoneInvoiceService::class.java)
                        }
                    }
                }
                return sInstance!!
            }
    }
}