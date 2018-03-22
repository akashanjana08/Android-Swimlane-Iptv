package shop.tv.rsys.com.tvapplication.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by akash.sharma on 2/26/2018.
 */

public class TimeFormat {


    public static String getHourMin()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM  | HH:mm:ss");
        String formatedTime = df.format(c.getTime());
        c.clear();
        return formatedTime;
    }

    public static String getDatewFormat()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM ");
        String formatedTime = df.format(c.getTime());
        c.clear();
        return formatedTime;
    }

}
