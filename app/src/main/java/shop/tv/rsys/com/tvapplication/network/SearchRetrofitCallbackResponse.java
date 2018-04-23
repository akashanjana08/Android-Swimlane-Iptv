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
import shop.tv.rsys.com.tvapplication.model.MovieDetailsModel;
import shop.tv.rsys.com.tvapplication.moviesearch.SearchResponsecallback;

/**
 * Created by akash.sharma on 12/15/2017.
 */

public class SearchRetrofitCallbackResponse {

    public static void getNetworkResponse(final SearchResponsecallback responseCallback, Map<String, String> queryMap, final Context mContext, final ProgressBar progressBar) {
        RetrofirApiInterface apiService = RetrofitApiClient.getApiClientForMds(BaseUrl.mdsBaseUrl).create(RetrofirApiInterface.class);
        Call<MovieDetailsModel> call = apiService.getMovieDetailsByTitle(queryMap);
        call.enqueue(new Callback<MovieDetailsModel>() {
            @Override
            public void onResponse(Call<MovieDetailsModel> call, Response<MovieDetailsModel> response) {
                progressBar.setVisibility(View.GONE);
                try {

                    List<MovieDetailsModel.Movie> btaMovies = response.body().getMovie();
                    responseCallback.getResponse(btaMovies);
                    if (btaMovies == null) {
                        Toast.makeText(mContext, "Movie Not Found", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Exception " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDetailsModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("RetrofitFail", t.toString());
                Toast.makeText(mContext, "Failuer " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}