package shop.tv.rsys.com.tvapplication.test;

import android.graphics.Rect;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v17.leanback.widget.PresenterSwitcher;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
/**
 * Created by akash.sharma on 1/4/2018.
 */

public class MyHorizontalHoverCardSwitcher extends PresenterSwitcher {
    // left and right of selected card view
    int mCardLeft, mCardRight;

    private int[] mTmpOffsets = new int[2];
    private Rect mTmpRect = new Rect();

    @Override
    protected void insertView(View view) {
        // append hovercard to the end of container
        getParentViewGroup().addView(view);
    }

    @Override
    protected void onViewSelected(View view) {
        int rightLimit = getParentViewGroup().getWidth() - getParentViewGroup().getPaddingRight();
        int leftLimit = getParentViewGroup().getPaddingLeft();
        // measure the hover card width; if it's too large, align hover card
        // end edge with row view's end edge, otherwise align start edges.
        view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
        boolean isRtl = ViewCompat.getLayoutDirection(view) == View.LAYOUT_DIRECTION_RTL;
        if (!isRtl && mCardLeft + view.getMeasuredWidth() > rightLimit) {
            params.leftMargin = rightLimit  - view.getMeasuredWidth();
        } else if (isRtl && mCardLeft < leftLimit) {
            params.leftMargin = leftLimit;
        } else if (isRtl) {
            params.leftMargin = mCardRight - view.getMeasuredWidth();
        } else {
            params.leftMargin = mCardLeft;
        }
        view.requestLayout();
    }

    /**
     * Select a childView inside a grid view and create/bind a corresponding hover card view
     * for the object.
     */
    public void select(HorizontalGridView gridView, View childView, Object object) {
        ViewGroup parent = getParentViewGroup();
        gridView.getViewSelectedOffsets(childView, mTmpOffsets);
        mTmpRect.set(0, 0, childView.getWidth(), childView.getHeight());
        parent.offsetDescendantRectToMyCoords(childView, mTmpRect);
        mCardLeft = mTmpRect.left - mTmpOffsets[0];
        mCardRight = mTmpRect.right - mTmpOffsets[0];
        select(object);
    }
}

