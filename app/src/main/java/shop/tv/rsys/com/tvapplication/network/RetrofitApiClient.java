package shop.tv.rsys.com.tvapplication.network;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import shop.tv.rsys.com.tvapplication.common.BaseUrl;

/**
 * Created by akash.sharma on 12/15/2017.
 */

public class RetrofitApiClient {
    private static Retrofit retrofit,mdsRetrofit;
    public static Retrofit getApiClient(String baseUrl)
    {
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(new OkHttpClient())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getApiClientForMds(String baseUrl)
    {
        if(mdsRetrofit==null){
            mdsRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(new OkHttpClient())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
        }
        return mdsRetrofit;
    }
}
