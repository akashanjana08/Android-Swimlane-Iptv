package shop.tv.rsys.com.tvapplication.ui.fontviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by akash.sharma on 12/20/2017.
 */

public class TextViewLight extends TextView {

    public TextViewLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewLight(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Proximus-Light.otf");
        setTypeface(tf ,0);

    }

}