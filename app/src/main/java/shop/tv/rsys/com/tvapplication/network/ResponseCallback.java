package shop.tv.rsys.com.tvapplication.network;

import java.util.List;

import shop.tv.rsys.com.tvapplication.model.BtaResponseModel;

/**
 * Created by akash.sharma on 12/15/2017.
 */

public interface ResponseCallback {
     void getResponse(List<BtaResponseModel.Movie> list);
}
