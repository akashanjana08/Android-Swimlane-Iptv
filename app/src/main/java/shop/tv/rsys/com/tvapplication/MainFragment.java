/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package shop.tv.rsys.com.tvapplication;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.BrowseFrameLayout;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import shop.tv.rsys.com.tvapplication.customheader.IconHeaderItem;
import shop.tv.rsys.com.tvapplication.hub.HubActivity;

public class MainFragment extends BrowseFragment {
    private static final String TAG = "MainFragment";

    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private static final int GRID_ITEM_WIDTH = 200;
    private static final int GRID_ITEM_HEIGHT = 200;
    private static final int NUM_ROWS = 2;
    private static final int NUM_COLS = 10;
    private final Handler mHandler = new Handler();
    private ArrayObjectAdapter mRowsAdapter;
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private URI mBackgroundURI;
    private BackgroundManager mBackgroundManager;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onActivityCreated(savedInstanceState);
        prepareBackgroundManager();
        setupUIElements();
        loadRows();
        setupEventListeners();
        initFocusManagement();
       // mBrowseFrame.setOnChildFocusListener(mOnChildFocusListener);
       // setSelectedPosition(3, true, new ListRowPresenter.SelectItemViewHolderTask(4));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mBackgroundTimer) {
            Log.d(TAG, "onDestroy: " + mBackgroundTimer.toString());
            mBackgroundTimer.cancel();
        }
    }

    private void loadRows() {
        List<Movie> list = MovieList.setupMovies();
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        CardPresenter cardPresenter = new CardPresenter(getActivity());
        int i;
        for (i = 0; i < NUM_ROWS; i++) {
            if (i != 0) {
                Collections.shuffle(list);
            }
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            for (int j = 0; j < NUM_COLS; j++) {
                  listRowAdapter.add(list.get(j % 5));
            }
            IconHeaderItem gridItemPresenterHeader = new IconHeaderItem(i, MovieList.MOVIE_CATEGORY[i], R.drawable.ic_assistant_black_24dp);
            // HeaderItem header = new HeaderItem(i, MovieList.MOVIE_CATEGORY[i]);
            mRowsAdapter.add(new ListRow(gridItemPresenterHeader, listRowAdapter));
        }
        setAdapter(mRowsAdapter);

    }

    private void prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mDefaultBackground = getResources().getDrawable(R.drawable.default_background);
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void setupUIElements() {
        // setBadgeDrawable(getActivity().getResources().getDrawable(
        // R.drawable.videos_by_google_banner));
        setTitle(""); // Badge, when set, takes precedent
        // over title
        setHeadersState(HEADERS_DISABLED);
        setHeadersTransitionOnBackEnabled(false);
        // set fastLane (or headers) background color
        setBrandColor(getResources().getColor(R.color.fastlane_background));
        // set search icon color
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));
        /*setHeaderPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object o) {
                return new IconHeaderItemPresenter(getActivity());
            }
        });*/
    }

    private void setupEventListeners() {
        /*setOnSearchClickedListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement your own in-app search", Toast.LENGTH_LONG)
                        .show();
            }
        });*/

        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());

        setBrowseTransitionListener(new BrowseTransitionListener(){


        });

    }

    protected void updateBackground(String uri) {
        int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;
        Glide.with(getActivity())
                .load(uri)
                .centerCrop()
                .error(mDefaultBackground)
                .into(new SimpleTarget<GlideDrawable>(width, height) {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable>
                                                        glideAnimation) {
                        mBackgroundManager.setDrawable(resource);
                    }
                });
        mBackgroundTimer.cancel();
    }

    private void startBackgroundTimer() {
        if (null != mBackgroundTimer) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof Movie) {
                Movie movie = (Movie) item;
                Log.d(TAG, "Item: " + item.toString());
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, movie);

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        DetailsActivity.SHARED_ELEMENT_NAME).toBundle();
                getActivity().startActivity(intent, bundle);
            } else if (item instanceof String) {
                if (((String) item).indexOf(getString(R.string.error_fragment)) >= 0) {
                    Intent intent = new Intent(getActivity(), BrowseErrorActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), ((String) item), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Movie) {
                mBackgroundURI = ((Movie) item).getBackgroundImageURI();
                startBackgroundTimer();
            }
        }
    }

    private class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mBackgroundURI != null) {
                        updateBackground(mBackgroundURI.toString());
                    }
                }
            });

        }
    }

    private class GridItemPresenter extends Presenter {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
          //  view.setBackgroundColor(getResources().getColor(R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {
        }
    }

  /*  @Override
    public void onLoadFinished(Loader<LinkedHashMap<String, List<Movie>>> loader, LinkedHashMap<String, List<Movie>> data) {

        IconHeaderItem header = new IconHeaderItem(index, entry.getKey(), R.drawable.ic_play_arrow_white_48dp);
        index++;
        mVideoListRowArray.add(new ListRow(header, cardRowAdapter));

    }*/

    private final int maxHookIntoFocusTries = 5;
    private int hookIntoFocusTries = 0;

    private void initFocusManagement() {
        View view = getView();
        Handler handler = new Handler();
        if(view == null){
            //Wait for the view to be added
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    initFocusManagement();
                }
            };
            handler.postDelayed(runnable, 250);
        }
        if (view instanceof ViewGroup) {
            boolean found = hookIntoFocusSearch((ViewGroup) view);
            if ( found ){
               // Timber.d("Successfully fixed focus");   //This is just a log
            }else if(hookIntoFocusTries < maxHookIntoFocusTries){
                //Allow multiple attempts to hook into the focus system
                //I want to say this was needed for when the browse fragment was
                //created but the child content hadn't been populated yet.
                //Been a while since I've messed with this code though
                hookIntoFocusTries++;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initFocusManagement();
                    }
                }, 250);
            }
        }
    }

    private boolean hookIntoFocusSearch(ViewGroup vg) {
        boolean found = false;
        for ( int i=0; i<vg.getChildCount(); i++ ) {
            View view = vg.getChildAt(i);
            if ( view instanceof BrowseFrameLayout) {
                BrowseFrameLayout bfl = (BrowseFrameLayout)view;
                bfl.setOnFocusSearchListener(new BrowseFrameLayout.OnFocusSearchListener() {
                    @Override
                    public View onFocusSearch(View focused, int direction) {
                        if ( direction == View.FOCUS_UP ) {
                              // return getLastHeaderFocusView();
                        }
                        if ( direction == View.FOCUS_DOWN ) {
                              // Toast.makeText(getActivity() , "On BrowseFragment" , Toast.LENGTH_LONG).show();
                            return null;
                        }else {
                            return null;
                        }
                    }
                });
                found = true;
                break;
            } else if ( view instanceof ViewGroup ) {
                boolean foundInRecurse = hookIntoFocusSearch((ViewGroup)view);
                if ( foundInRecurse ) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }


   /* private View getLastHeaderFocusView()
    {
        int lastHeaderFocus = HubActivity.headerFocus;
        int viewFocusId=0;
        switch(lastHeaderFocus){
            case 0:
                viewFocusId =  R.id.home_textview;
                break;
            case 1:
                viewFocusId =  R.id.mylib_textview;
                break;
            case 2:
                viewFocusId =  R.id.shop_textview;
                break;
            default:
        }
        View view = getActivity().findViewById(viewFocusId);
        return  view;
    }*/
}

