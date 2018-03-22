package shop.tv.rsys.com.tvapplication.network;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import shop.tv.rsys.com.tvapplication.model.BtaResponseModel;
import shop.tv.rsys.com.tvapplication.model.MovieDetailsModel;

/**
 * Created by akash.sharma on 12/15/2017.
 */

public interface RetrofirApiInterface {

    @GET("broker/bta/getDetails")
    Call<BtaResponseModel> getBtaResponse(@QueryMap Map<String,String> queryMap);

    @GET("HESMetadataServer/CoDServlet")
    Call<MovieDetailsModel> getMovieDetailsByTitle(@QueryMap Map<String,String> queryMap);
}
