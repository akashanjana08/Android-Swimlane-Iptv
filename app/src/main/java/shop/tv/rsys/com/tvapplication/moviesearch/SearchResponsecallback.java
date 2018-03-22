package shop.tv.rsys.com.tvapplication.moviesearch;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import shop.tv.rsys.com.tvapplication.model.BtaResponseModel;
import shop.tv.rsys.com.tvapplication.model.MovieDetailsModel;

/**
 * Created by akash.sharma on 12/15/2017.
 */

public interface SearchResponsecallback {

    void getResponse(List<MovieDetailsModel.Movie> list);
}
