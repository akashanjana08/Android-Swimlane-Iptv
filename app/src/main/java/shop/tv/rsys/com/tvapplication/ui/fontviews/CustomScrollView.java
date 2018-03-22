package shop.tv.rsys.com.tvapplication.ui.fontviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * Created by akash.sharma on 2/1/2018.
 */

public class CustomScrollView extends ScrollView {

    Context mContext;

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Toast.makeText(mContext,"OnKeyScollDown",Toast.LENGTH_SHORT).show();
        return super.onKeyDown(keyCode, event);

    }
}
