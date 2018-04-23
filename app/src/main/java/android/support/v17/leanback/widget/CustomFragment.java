package android.support.v17.leanback.widget;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.FocusHighlight;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.PageRow;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shop.tv.rsys.com.tvapplication.BrowseErrorActivity;
import shop.tv.rsys.com.tvapplication.CardPresenter;
import shop.tv.rsys.com.tvapplication.DetailsActivity;
import shop.tv.rsys.com.tvapplication.MainFragment;
import shop.tv.rsys.com.tvapplication.Movie;
import shop.tv.rsys.com.tvapplication.R;
import shop.tv.rsys.com.tvapplication.common.GlobalState;
import shop.tv.rsys.com.tvapplication.customheader.IconHeaderItem;
import shop.tv.rsys.com.tvapplication.customheader.IconHeaderItemPresenter;
import shop.tv.rsys.com.tvapplication.model.BtaResponseModel;
import shop.tv.rsys.com.tvapplication.model.MovieDetailsModel;
import shop.tv.rsys.com.tvapplication.moviesearch.SearchResponsecallback;
import shop.tv.rsys.com.tvapplication.network.ResponseCallback;
import shop.tv.rsys.com.tvapplication.network.RetrofitCallbackResponse;
import shop.tv.rsys.com.tvapplication.network.SearchRetrofitCallbackResponse;
import shop.tv.rsys.com.tvapplication.parser.MovieListParsing;

/**
 * Created by Akash.Sharma on 12/7/2017.
 */

public class CustomFragment extends BrowseFragment {

    private static final long HEADER_ID_1 = 1;
    private static final String HEADER_NAME_1 = "populair";
    private static final long HEADER_ID_2 = 2;
    private static final String HEADER_NAME_2 = "nieuw";
    private static final long HEADER_ID_3 = 3;
    private static final String HEADER_NAME_3 = "recent";
    private static final long HEADER_ID_4 = 4;
    private static final String HEADER_NAME_4 = "a tot z";
    private BackgroundManager mBackgroundManager;
    private ArrayObjectAdapter mRowsAdapter;
    static List<Movie> list;
    static ProgressBar progressBar;
    private static ItemViewClickedListener itemViewClickedListener;
    private static  int SHOP_PAGE = 0;
    private static String speechText="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUi();
        loadData();
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        getMainFragmentRegistry().registerFragment(PageRow.class,
                new PageRowFragmentFactory(mBackgroundManager));

        SHOP_PAGE = getActivity().getIntent().getIntExtra("MOVIE_CATAGORY",0);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar = (ProgressBar)getActivity().findViewById(R.id.progressBar);
        setupEventListeners();
    }


    private  void setupEventListeners() {
        /*setOnSearchClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement your own in-app search", Toast.LENGTH_LONG)
                        .show();
            }
        });*/
        initFocusManagement();
        itemViewClickedListener = new ItemViewClickedListener();
    }


    private void setupUi() {
        // setBadgeDrawable(getActivity().getResources().getDrawable(
        // R.drawable.videos_by_google_banner));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(Color.argb(1,1,2,38));
       // setBrandColor(getResources().getColor(R.color.fastlane_background));
        setTitle("");
        setHeaderPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object o) {
                return new IconHeaderItemPresenter();
            }
        });
        //prepareEntranceTransition();
    }

    private  class PageRowFragmentFactory extends BrowseFragment.FragmentFactory {
        private final BackgroundManager mBackgroundManager;
        PageRowFragmentFactory(BackgroundManager backgroundManager) {
            this.mBackgroundManager = backgroundManager;
        }
        @Override
        public Fragment createFragment(Object rowObj) {
            Row row = (Row)rowObj;
            mBackgroundManager.setDrawable(null);

            if (row.getHeaderItem().getId() == HEADER_ID_1) {
                return new SampleFragmentA("categoryPrimary");
            } else if (row.getHeaderItem().getId() == HEADER_ID_2) {
                return new SampleFragmentA("pureSortName");
            } else if (row.getHeaderItem().getId() == HEADER_ID_3) {
                return new SampleFragmentA("pureReleaseDateRev");
            }else if (row.getHeaderItem().getId() == HEADER_ID_4) {
                return new SampleFragmentA("pureReleaseDateRev");
            }
            throw new IllegalArgumentException(String.format("Invalid row %s", rowObj));
        }
    }

    private void loadData() {


        speechText = getActivity().getIntent().getStringExtra("SPEECH_TEXT");

        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        setAdapter(mRowsAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                createRows();
                startEntranceTransition();
            }
        }, 2000);
    }

    private void createRows() {
        IconHeaderItem gridItemPresenterHeader1 = new IconHeaderItem(HEADER_ID_1, HEADER_NAME_1,R.id.header_radio_button);
        PageRow pageRow1 = new PageRow(gridItemPresenterHeader1);
        mRowsAdapter.add(pageRow1);

        IconHeaderItem gridItemPresenterHeader2 = new IconHeaderItem(HEADER_ID_2, HEADER_NAME_2,R.id.header_radio_button);
        PageRow pageRow2 = new PageRow(gridItemPresenterHeader2);
        mRowsAdapter.add(pageRow2);

        IconHeaderItem gridItemPresenterHeader3 = new IconHeaderItem(HEADER_ID_3, HEADER_NAME_3,R.id.header_radio_button);
        PageRow pageRow3 = new PageRow(gridItemPresenterHeader3);
        mRowsAdapter.add(pageRow3);

        IconHeaderItem gridItemPresenterHeader4 = new IconHeaderItem(HEADER_ID_4, HEADER_NAME_4,R.id.header_radio_button);
        PageRow pageRow4 = new PageRow(gridItemPresenterHeader4);
        mRowsAdapter.add(pageRow4);
    }


    /**
     * Page fragment embeds a rows fragment.
     */
    public static class SampleFragmentA extends RowsFragment {
        private  ArrayObjectAdapter mRowsAdapter;
        private static final int NUM_COLS = 10;
        String sortValue=null;
        public SampleFragmentA(){
        }
        public SampleFragmentA(String sortValue) {
            this.sortValue=sortValue;
            mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

            setAdapter(mRowsAdapter);
            setOnItemViewClickedListener(itemViewClickedListener);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            progressBar.setVisibility(View.VISIBLE);
            if(SHOP_PAGE == 0) {
                RetrofitCallbackResponse.getNetworkResponse(new ResponseCallback() {
                    @Override
                    public void getResponse(List<BtaResponseModel.Movie> listdata) {
                        try {

                            list = MovieListParsing.getMovieList(listdata);
                            loadRows();
                            //setSelectedPosition(0, true, new ListRowPresenter.SelectItemViewHolderTask(5));
                            getMainFragmentAdapter().getFragmentHost().notifyDataReady(getMainFragmentAdapter());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, getQueryMap(sortValue),getActivity(),progressBar);

            }
            else if(SHOP_PAGE == 1){
                SearchRetrofitCallbackResponse.getNetworkResponse(new SearchResponsecallback() {
                    @Override
                    public void getResponse(List<MovieDetailsModel.Movie> listdata) {
                        try {

                            try {

                                list = MovieListParsing.getMovieSearchDetails(listdata);
                                loadRows();
                                getMainFragmentAdapter().getFragmentHost().notifyDataReady(getMainFragmentAdapter());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, getQueryMap(),getActivity(),progressBar);
            }
        }


        private void loadRows() {
            mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter(FocusHighlight.ZOOM_FACTOR_LARGE));
            CardPresenter cardPresenter = new CardPresenter(getActivity());
            int i,elemets=0;
            for (i = 0; i < list.size()/NUM_COLS; i++) {
                ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                for (int j = 0; j < NUM_COLS; j++) {

                    listRowAdapter.add(list.get(elemets));
                    elemets++;
                }
                mRowsAdapter.add(new ListRow(null, listRowAdapter));
            }
            if((list.size()%10)!=0)
            {
                ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                try {
                    for (int j = 0; j < ((list.size() % 10)); j++) {
                        listRowAdapter.add(list.get(elemets));
                        elemets++;
                    }
                }
                catch (ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                }
                mRowsAdapter.add(new ListRow(null, listRowAdapter));
            }
            setAdapter(mRowsAdapter);
        }
    }


    private final  class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof Movie) {
                Movie movie = (Movie) item;
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, movie);
                intent.putExtra(GlobalState.HEADER_TITLE_INTENT_KEY,"Movie Detail");
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


    private static Map<String,String> getQueryMap(String sortValue)
    {
        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("fc_Language","nl");
        queryMap.put("fc_CategoryID","83802703-d3d1-42a7-b7c6-f25f1a593d84");
        queryMap.put("fc_HDCapable","true");
        queryMap.put("c_ContentTypes","CoD");
        queryMap.put("fc_StartIndex","0");
        queryMap.put("fc_HowMany","60");
        queryMap.put("fc_UnlockedPackagesOnly","true");
        queryMap.put("fc_Sort",sortValue);
        queryMap.put("ac_InvoicePrices","true");
        queryMap.put("MAC","0022CEC5725B");
        return queryMap;
    }

    private static Map<String,String> getQueryMap()
    {
        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("searchScope","title");
        queryMap.put("value",speechText);
        queryMap.put("operation","BEGINSWITH");
        queryMap.put("sort","name");
        queryMap.put("subtypesVOD","movie");
        return queryMap;
    }

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
                             //return getActivity().findViewById(R.id.voice_search_imageview);
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
}
