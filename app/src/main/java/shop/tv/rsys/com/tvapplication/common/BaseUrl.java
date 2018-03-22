package shop.tv.rsys.com.tvapplication.common;

/**
 * Created by akash.sharma on 2/16/2018.
 */

public class BaseUrl {
    public final static String tmbaseUrl          = "http://10.67.194.30:8085/";      // BTA Movie CALL
    public final static String mdsBaseUrl         = "http://10.67.194.40/";           // MDS search movie by title
    public final static String imageBaseUrl       = "http://10.67.191.50:8085/";
    public final static String mdsImageBaseUrl    = "http://10.67.194.30/";


    public final static String btaImagePathUrl   = imageBaseUrl+"posterserver/poster/vod/";
    public final static String mdsImagePathUrl   = mdsImageBaseUrl+"movie-images/";
}
