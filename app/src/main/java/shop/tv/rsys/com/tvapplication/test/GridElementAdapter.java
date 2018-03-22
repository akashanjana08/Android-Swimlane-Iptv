package shop.tv.rsys.com.tvapplication.test;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import org.simpleframework.xml.stream.Position;

import java.util.ArrayList;
import java.util.List;

import shop.tv.rsys.com.tvapplication.R;

/**
 * Created by akash.sharma on 1/4/2018.
 */

public class GridElementAdapter extends RecyclerView.Adapter<GridElementAdapter.SimpleViewHolder>{


    private Context context;
    private List<String> elements;
    private int lastPosition = -1;

    public GridElementAdapter(Context context,int startPosition){
        this.context = context;
        this.elements = new ArrayList<>();
        // Fill dummy list
        for(int i = 0; i < 20 ; i++){
            this.elements.add(i, "Position : " + startPosition++);
        }
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final Button button;

        public SimpleViewHolder(View view) {
            super(view);
            button = (Button) view.findViewById(R.id.button);
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(this.context).inflate(R.layout.grid_element, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.button.setText(elements.get(position));
        // Here you apply the animation when the view is bound
        setAnimation(holder.itemView, position);
        holder.button.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Position =" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return this.elements.size();
    }


    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        int pos = position % 2 * 2;
        return pos;
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}