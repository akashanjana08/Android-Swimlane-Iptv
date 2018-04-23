package shop.tv.rsys.com.tvapplication.hub;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.MyHorizontalGridView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintStream;

import shop.tv.rsys.com.tvapplication.R;
import shop.tv.rsys.com.tvapplication.hub.adapter.GridElementAdapter;
import shop.tv.rsys.com.tvapplication.ui.fontviews.CustomScrollView;


/**
 * Created by akash.sharma on 1/24/2018.
 */

public class GridHubFragment extends Fragment implements IKeyEventListener {

    private RecyclerView horizontalGridView1, horizontalGridView2, horizontalGridView3, horizontalGridView4;
    private RelativeLayout firstRelativeGrid, secondRelativeGrid;
    private View currentFocusView;
    GridElementAdapter adapter1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_horizontal_grid, container, false);

        horizontalGridView1 = (RecyclerView) view.findViewById(R.id.gridView1);
        horizontalGridView2 = (RecyclerView) view.findViewById(R.id.gridView2);
        firstRelativeGrid = (RelativeLayout) view.findViewById(R.id.firstRelativeGrid);
        secondRelativeGrid = (RelativeLayout) view.findViewById(R.id.secondRelativeGrid);

        horizontalGridView1.setFocusable(false);
        horizontalGridView2.setFocusable(false);


        adapter1 = new GridElementAdapter(getActivity(), 0, 250, 314, R.layout.grid_element, true);
        GridElementAdapter adapter2 = new GridElementAdapter(getActivity(), 0, 300, 350, R.layout.focus_grid_element, false);
        GridElementAdapter adapter3 = new GridElementAdapter(getActivity(), 0, 250, 314, R.layout.grid_element, false);


        horizontalGridView1.setAdapter(adapter1);
        SpeedyLinearLayoutManager linearLayoutManager = new SpeedyLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horizontalGridView1.setLayoutManager(linearLayoutManager);


        horizontalGridView2.setAdapter(adapter2);
        SpeedyLinearLayoutManager linearLayoutManager2 = new SpeedyLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horizontalGridView2.setLayoutManager(linearLayoutManager2);
        // horizontalGridView2.setFocusable(false);

        /** second row grid */

        horizontalGridView3 = (RecyclerView) view.findViewById(R.id.gridView3);
        horizontalGridView4 = (RecyclerView) view.findViewById(R.id.gridView4);
        horizontalGridView3.setFocusable(false);
        horizontalGridView4.setFocusable(false);

        horizontalGridView3.setAdapter(adapter3);
        SpeedyLinearLayoutManager linearLayoutManager3 = new SpeedyLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horizontalGridView3.setLayoutManager(linearLayoutManager3);

        // horizontalGridView4.setFocusable(false);
        horizontalGridView4.setAdapter(adapter2);
        SpeedyLinearLayoutManager linearLayoutManager4 = new SpeedyLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horizontalGridView4.setLayoutManager(linearLayoutManager4);


        horizontalGridView2.setVisibility(View.VISIBLE);
        horizontalGridView4.setVisibility(View.INVISIBLE);

        horizontalGridView1.setItemAnimator(null);
        horizontalGridView2.setItemAnimator(null);
        horizontalGridView3.setItemAnimator(null);
        horizontalGridView4.setItemAnimator(null);

        return view;
    }

    public void autoScroll() {
        final int speedScroll = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (count == adapter1.getItemCount())
                    count = 0;
                if (count < adapter1.getItemCount()) {
                    horizontalGridView1.smoothScrollToPosition(++count);
                    handler.postDelayed(this, speedScroll);
                }
            }
        };
        handler.postDelayed(runnable, speedScroll);
    }

    int i = 0, j = 0;

    /**
     * Override method of {@link IKeyEventListener}
     */
    @Override
    public void onKeyDownEventKey(final KeyEvent event, final FrameLayout mHeaderMenuFramelayout) {

        System.out.println("GridView-1  " + horizontalGridView1.hasFocus());
        System.out.println("GridView-2  " + horizontalGridView2.hasFocus());
        System.out.println("GridView-3  " + horizontalGridView3.hasFocus());
        System.out.println("GridView-4  " + horizontalGridView4.hasFocus());
        System.out.println("==========================================================================");
        System.out.println("Current Focus Now" + getActivity().getCurrentFocus());
        final int keyCode = event.getKeyCode();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if ((horizontalGridView2.getVisibility() == View.VISIBLE) && (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)) {
                    secondRelativeGrid.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                } else if ((horizontalGridView4.getVisibility() == View.VISIBLE) && (keyCode == KeyEvent.KEYCODE_DPAD_UP)) {
                    firstRelativeGrid.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
                }


                if (keyCode == KeyEvent.KEYCODE_DPAD_UP && (horizontalGridView2.getVisibility() == View.VISIBLE)) {
                    getFragmentManager().findFragmentByTag("Header_Menu_Fragment").getView().findViewById(R.id.home_textview).requestFocus();
                }

                if ((horizontalGridView1.hasFocus() || horizontalGridView2.hasFocus())) {
                    if ((horizontalGridView2.getVisibility() != View.VISIBLE)) {
                        slideToTop(horizontalGridView2);
                        if (currentFocusView.getId() == R.id.imagecardview)
                            slideToBottom(horizontalGridView4);
                    }

                    horizontalGridView2.setVisibility(View.VISIBLE);
                    horizontalGridView4.setVisibility(View.INVISIBLE);

                } else if (horizontalGridView3.hasFocus() || horizontalGridView4.hasFocus()) {
                    if ((horizontalGridView4.getVisibility() != View.VISIBLE)) {
                        slideToTop(horizontalGridView4);
                        slideToBottom(horizontalGridView2);
                    }
                    horizontalGridView2.setVisibility(View.INVISIBLE);
                    horizontalGridView4.setVisibility(View.VISIBLE);

                } else {
                    horizontalGridView2.clearAnimation();
                    horizontalGridView4.clearAnimation();
                    horizontalGridView2.setVisibility(View.INVISIBLE);
                    horizontalGridView4.setVisibility(View.INVISIBLE);
                    if ((horizontalGridView4.getVisibility() == View.VISIBLE) && (horizontalGridView2.getVisibility() == View.VISIBLE)) {
                        slideToBottom(horizontalGridView2);
                        slideToBottom(horizontalGridView4);
                    }
                }
                currentFocusView = getActivity().getCurrentFocus();
            }
        });
        gridscrolling(keyCode, mHeaderMenuFramelayout);
    }

    @Override
    public void onKeyUpEventKey(KeyEvent event, FrameLayout mFramelayout) {
    }


    private void gridscrolling(int keyCode, FrameLayout mHeaderMenuFramelayout) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            mHeaderMenuFramelayout.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        }

        if ((horizontalGridView2.getVisibility() == View.VISIBLE) && (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)) {
            horizontalGridView1.smoothScrollBy(-horizontalGridView1.getChildAt(0).getWidth(), 0);
            horizontalGridView2.smoothScrollBy(-horizontalGridView2.getChildAt(0).getWidth(), 0);
            if (i != 0) {
                i--;
                secondRelativeGrid.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
            }

            horizontalGridView1.smoothScrollToPosition(i);
            horizontalGridView2.smoothScrollToPosition(i);

        } else if ((horizontalGridView4.getVisibility() == View.VISIBLE) && (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)) {

            horizontalGridView3.smoothScrollBy(-horizontalGridView3.getChildAt(0).getWidth(), 0);
            horizontalGridView4.smoothScrollBy(-horizontalGridView3.getChildAt(0).getWidth(), 0);
            if (j != 0) {
                j--;
                firstRelativeGrid.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
            }
            horizontalGridView3.smoothScrollToPosition(j);
            horizontalGridView4.smoothScrollToPosition(j);
        } else if ((horizontalGridView2.getVisibility() == View.VISIBLE) && (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)) {
            horizontalGridView1.smoothScrollBy(horizontalGridView1.getChildAt(0).getWidth(), 0);
            horizontalGridView2.smoothScrollBy(horizontalGridView2.getChildAt(0).getWidth(), 0);
            if (i != (horizontalGridView2.getAdapter().getItemCount() - 1) && i < (horizontalGridView2.getAdapter().getItemCount() - 1)) {
                i++;
                secondRelativeGrid.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                mHeaderMenuFramelayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            }
            horizontalGridView1.smoothScrollToPosition(i);
            horizontalGridView2.smoothScrollToPosition(i);
        } else if ((horizontalGridView4.getVisibility() == View.VISIBLE) && (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)) {
            horizontalGridView3.smoothScrollBy(horizontalGridView3.getChildAt(0).getWidth(), 0);
            horizontalGridView4.smoothScrollBy(horizontalGridView3.getChildAt(0).getWidth(), 0);
            if (j != (horizontalGridView3.getAdapter().getItemCount() - 1) && j < (horizontalGridView3.getAdapter().getItemCount() - 1)) {
                j++;
                firstRelativeGrid.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                mHeaderMenuFramelayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            }
            horizontalGridView3.smoothScrollToPosition(j);
            horizontalGridView4.smoothScrollToPosition(j);
        }
    }


    public class SpeedyLinearLayoutManager extends LinearLayoutManager {

        private static final float MILLISECONDS_PER_INCH = 100f; //default is 25f (bigger = slower)

        public SpeedyLinearLayoutManager(Context context) {
            super(context);
        }

        public SpeedyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public SpeedyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {

            final LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

                @Override
                public PointF computeScrollVectorForPosition(int targetPosition) {
                    return SpeedyLinearLayoutManager.this.computeScrollVectorForPosition(targetPosition);
                }

                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
                }
            };

            linearSmoothScroller.setTargetPosition(position);
            startSmoothScroll(linearSmoothScroller);
        }
    }


    // To animate view slide out from left to right
    public void slideToRight(View view) {
        TranslateAnimation animate = new TranslateAnimation(0, view.getWidth(), 0, 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    // To animate view slide out from right to left
    public void slideToLeft(View view) {
        TranslateAnimation animate = new TranslateAnimation(0, -view.getWidth(), 0, 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    // To animate view slide out from top to bottom
    public void slideToBottom(final View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, 100);
        animate.setDuration(50);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.INVISIBLE);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                horizontalGridView2.clearAnimation();
                horizontalGridView4.clearAnimation();
                if (view.getId() == R.id.gridView2)
                    horizontalGridView2.setVisibility(View.GONE);
                else
                    horizontalGridView4.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    // To animate view slide out from bottom to top
    public void slideToTop(View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 100, 0);
        animate.setDuration(50);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        //view.setVisibility(View.INVISIBLE);
    }
}