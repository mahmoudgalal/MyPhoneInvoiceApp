package com.mahmoudgalal.myphoneinvoice;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private String generateRandomToken(int tokenLength){
        StringBuilder sb = new StringBuilder();
        sb.append("token=");
        Random random = new Random();
        for (int i = 0; i < tokenLength; i++) {
            int ascii = 'A'+random.nextInt(26);
            sb.append((char)ascii);
        }
        System.out.println("Generated token:"+sb.toString());
        return  sb.toString();
    }

//    @Test
//    public void checkResponse(){
//
//        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = RequestBody.create(mediaType,
//                "");
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(0, TimeUnit.SECONDS)
//                .writeTimeout(0, TimeUnit.SECONDS)
//                .readTimeout(300, TimeUnit.SECONDS)
//                .callTimeout(100, TimeUnit.SECONDS)
//                .followRedirects(true)
//                .followSslRedirects(false)
//                .build();
//
//        Request request = new Request.Builder()
//                .url("https://billing.te.eg/ar-EG")
//                .get()
//                .addHeader("cache-control", "private")
//                .addHeader("Cookie","TS016e6d92=010aa23b1de7e9e694f5609790b75ff1bfcc8935cdaf5fde5be01243405e9304bdba0ce803b8c896b28111851f4ea5b934072186f4161747b39820c20d81de57340f066650e6a751b52186cf3d3fcdb113c6e90d22; path=/")
//                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) " +
//                        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
//
//                .build();
//        try {
//            Response response = client.newCall(request).execute();
//            System.out.println(String.format("Message:%s, code: %d",response.message()
//                    ,response.code()));
//            for(String st:response.headers().names()){
//                System.out.println(String.format("Header Name:%s : %s",
//                        st,response.headers().get(st)));
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }

    @Test
    public void addition_isCorrect() {


        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(0, TimeUnit.SECONDS)
                .writeTimeout(0, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .callTimeout(100, TimeUnit.SECONDS)
                .followRedirects(true)
                .followSslRedirects(false)
                .build();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType,
                "AreaCode=03&PhoneNumber=4277504&PinCode=&InquiryBy=telephone&AccountNo=");
        Request request = new Request.Builder()
                .url("https://billing.te.eg/api/Account/Inquiry")
                .post(body)
                //Accept-Encoding: gzip, deflate, br
                //Connection: keep-alive
                .addHeader("Upgrade", "HTTP/1.1")
               // .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("cache-control", "no-cache")
                //.addHeader("Cookie","TS016e6d92=010aa23b1db83c3da95a2f8fe59588dba1f043dfb3336755d643139b75f68ea4d7fe6d1bbaa3268359472dc23f0076fdcbd609944e25658bba19838e2dd87e5bc8af02761a6f47feb38de2a27c4fe63ebf6b6679fd")
                .addHeader("Cookie", generateRandomToken(32))
                        //"token=ZRRRD78847AC404D03D1D2BC82BC856c")//20190111200915135FB128777D4A95EA4821248B2BC8F5; path=/")

                //.addHeader("Cookie", "token=DE06D78847AC404D03D1D2BC82BC856920190111200915135FB128777D4A95EA4821248B2BC8F5; path=/")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
               // .addHeader("Origin", "https://billing.te.eg")
                //Content-Length: 71
               // .addHeader("Referer", "https://billing.te.eg/ar-eg")
                //.addHeader("Host", "billing.te.eg")
                //.addHeader("X-Requested-With", "XMLHttpRequest")
                //.addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        try {
               Response response = client.newCall(request).execute();
            System.out.println(String.format("Message:%s, code: %d",response.body().string()
                    ,response.code()));
            for(String st:response.headers().names()){
                System.out.println(String.format("Header Name:%s : %s",
                        st,response.headers().get(st)));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }


/*        final  AtomicBoolean isDone = new AtomicBoolean(false);
        String areaCode = "03",phoneNumber = "4277504";
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)

                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TelephoneInvoiceService service = retrofit.create(TelephoneInvoiceService.class);
        service.getTelephoneInvoices("telephone",areaCode,phoneNumber).
                enqueue(new Callback<ServiceResponse>() {
                    @Override
                    public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                        System.out.println(String.format("Message:%s, code: %d",response.message()
                        ,response.code()));
                        if(response.isSuccessful()) {

                        }
                        isDone.set(true);
                    }

                    @Override
                    public void onFailure(Call<ServiceResponse> call, Throwable t) {
                        System.out.println("Error:"+t.getMessage());
                        isDone.set(true);
                    }
                });
        while (!isDone.get()){
        }*/

    }
}