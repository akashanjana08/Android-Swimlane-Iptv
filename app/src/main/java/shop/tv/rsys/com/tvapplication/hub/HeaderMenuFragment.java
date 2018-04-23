package shop.tv.rsys.com.tvapplication.hub;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import shop.tv.rsys.com.tvapplication.MainActivity;
import shop.tv.rsys.com.tvapplication.R;
import shop.tv.rsys.com.tvapplication.common.GlobalState;

/**
 * Created by akash.sharma on 1/30/2018.
 */

public class HeaderMenuFragment extends Fragment implements View.OnFocusChangeListener{


    TextView homeTextview;
    TextView myLibtextview;
    TextView shopTextView;


    public static int headerFocus = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_header_menu , container , false);
        bindView(view);
        // ButterKnife.bind(this,view);
        //getWindow().getDecorView().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.gradient_main));
        homeTextview.setOnFocusChangeListener(this);
        myLibtextview.setOnFocusChangeListener(this);
        shopTextView.setOnFocusChangeListener(this);

        listenerView();
        return view;
    }


    void listenerView()
    {
        shopTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopIntent = new Intent(getActivity(), MainActivity.class);
                shopIntent.putExtra("MOVIE_CATAGORY",0);
                shopIntent.putExtra(GlobalState.HEADER_TITLE_INTENT_KEY,"Shop");
                startActivity(shopIntent);
            }
        });
    }




    void bindView(View view)
    {
        homeTextview = (TextView)view.findViewById(R.id.home_textview);
        myLibtextview = (TextView)view.findViewById(R.id.mylib_textview);
        shopTextView = (TextView)view.findViewById(R.id.shop_textview);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        switch (v.getId()) {
            case R.id.home_textview:
                headerFocus = 0;
                homeTextview.setTypeface(hasFocus ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
                break;
            case R.id.mylib_textview:
                headerFocus = 1;
                myLibtextview.setTypeface(hasFocus ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
                break;
            case R.id.shop_textview:
                headerFocus = 2;
                shopTextView.setTypeface(hasFocus ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
                break;
            default:
        }
    }


    public void shopClick(View view) {
        // TODO submit data to server...

    }
}