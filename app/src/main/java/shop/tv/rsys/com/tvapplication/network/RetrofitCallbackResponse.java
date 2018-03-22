package shop.tv.rsys.com.tvapplication.network;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shop.tv.rsys.com.tvapplication.common.BaseUrl;
import shop.tv.rsys.com.tvapplication.model.BtaResponseModel;

/**
 * Created by akash.sharma on 12/15/2017.
 */

public class RetrofitCallbackResponse {

    public static void getNetworkResponse(final ResponseCallback responseCallback, Map<String,String> queryMap, final Context mContext , final ProgressBar progressBar)
    {
        RetrofirApiInterface apiService = RetrofitApiClient.getApiClient(BaseUrl.tmbaseUrl).create(RetrofirApiInterface.class);
        Call<BtaResponseModel> call = apiService.getBtaResponse(queryMap);
        call.enqueue(new Callback<BtaResponseModel>() {
            @Override
            public void onResponse(Call<BtaResponseModel> call, Response<BtaResponseModel> response) {

                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    try {
                        List<BtaResponseModel.Movie> btaMovies = response.body().getMovie();
                        responseCallback.getResponse(btaMovies);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(mContext , "Exception "+e.toString() , Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    // error case
                    switch (response.code()) {
                        case 404:
                            Toast.makeText(mContext, "404 not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(mContext, "500 server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(mContext, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<BtaResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("RetrofitFail",t.toString());
                Toast.makeText(mContext, "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
