package shop.tv.rsys.com.tvapplication.hub;

import android.app.Fragment;
import android.view.KeyEvent;
import android.widget.FrameLayout;

/**
 * Created by akash.sharma on 1/24/2018.
 */

public interface IKeyEventListener {
    void onKeyDownEventKey(KeyEvent event , FrameLayout mFramelayout);
    void onKeyUpEventKey(KeyEvent event , FrameLayout mFramelayout);
}
