package shop.tv.rsys.com.tvapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import shop.tv.rsys.com.tvapplication.common.GlobalState;
import shop.tv.rsys.com.tvapplication.common.TimeFormat;

/**
 * Created by akash.sharma on 2/26/2018.
 */

public class LogoHeaderFragment extends Fragment {

    TextView dateTimeTextView, titleTextView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_logo_header, container, false);
        initViews(view);
        setViewsData();
        return view;
    }

    private void initViews(View view) {
        dateTimeTextView = (TextView) view.findViewById(R.id.time_text_view);
        titleTextView = (TextView) view.findViewById(R.id.title_text_view);
    }

    private void setViewsData() {

        String headerTitle = getActivity().getIntent().getStringExtra(GlobalState.HEADER_TITLE_INTENT_KEY);
        if(headerTitle!=null)
            titleTextView.setText(headerTitle);
        else
            titleTextView.setText("Hub");

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);

                        if (getActivity() == null) {
                            interrupt();
                            return;
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // update TextView here!
                                dateTimeTextView.setText("mar. " + TimeFormat.getHourMin());
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }

}
