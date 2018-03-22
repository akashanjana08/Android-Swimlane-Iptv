package shop.tv.rsys.com.tvapplication.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v17.leanback.widget.VerticalGridView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import shop.tv.rsys.com.tvapplication.R;

/**
 * Created by akash.sharma on 1/4/2018.
 */

public class HorizontalGridActivity extends Activity{

    private HorizontalGridView horizontalGridView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horizontal_grid);

        horizontalGridView1 = (HorizontalGridView) findViewById(R.id.gridView);

        horizontalGridView1.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Toast.makeText(HorizontalGridActivity.this,"onScrollStateChanged:"+newState,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
               // Toast.makeText(HorizontalGridActivity.this,"onScrolled:",Toast.LENGTH_SHORT).show();
                if (dx > 0) {
                    System.out.println("Scrolled Right");
                } else if (dx < 0) {
                    System.out.println("Scrolled Left");
                } else {
                    System.out.println("No Horizontal Scrolled");
                }

                if (dy > 0) {
                    System.out.println("Scrolled Downwards");
                } else if (dy < 0) {
                    System.out.println("Scrolled Upwards");
                } else {
                    System.out.println("No Vertical Scrolled");
                }
            }
        });
        GridElementAdapter adapter1 = new GridElementAdapter(this,0);
        horizontalGridView1.setAdapter(adapter1);
    }
}
