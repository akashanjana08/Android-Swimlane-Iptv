package shop.tv.rsys.com.tvapplication.hub.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import shop.tv.rsys.com.tvapplication.R;


/**
 * Created by akash.sharma on 1/4/2018.
 */

public class GridElementAdapter extends RecyclerView.Adapter<GridElementAdapter.SimpleViewHolder> {

    private Context context;
    private List<String> elements;
    private int lastPosition = -1;
    int itemHeight;
    int itemWidth;
    int resource;
    int posterImages[] = {R.drawable.movie_poster1, R.drawable.movie_poster2, R.drawable.movie_poster3, R.drawable.movie_poster4,
            R.drawable.movie_poster5, R.drawable.movie_poster6};

    String movieTitle[] = {"The Hangover", "Kong: Skull Island", "Ghostbusters", "The Grey: LIVE OR DIE ON THE DAY",
            "The Martian: 2015", "Fight Club: 1999"};

    int movieDescription[] = {R.string.the_hangover, R.string.kong_skull_iland, R.string.ghostbusters, R.string.the_grey,
            R.string.the_martian, R.string.fight_club};
    List<Integer> posterImageList;
    List<String>  movieTitleList;
    List<Integer> movieDescriptionList;
    private boolean requestFocus;


    public GridElementAdapter(Context context, int startPosition, int itemHeight, int itemWidth, int resource , boolean requestFocus) {
        this.context = context;
        this.itemHeight = itemHeight;
        this.itemWidth = itemWidth;
        this.resource = resource;
        this.requestFocus = requestFocus;
        posterImageList = new ArrayList<>();
        movieTitleList = new ArrayList<>();
        movieDescriptionList = new ArrayList<>();

        this.elements = new ArrayList<>();
        // Fill dummy list
        for (int i = 0; i < 10; i++) {
            this.elements.add(i, "Position : " + startPosition++);
            posterImageList.add(posterImages[i % 6]);
            movieTitleList.add(movieTitle[i % 6]);
            movieDescriptionList.add(movieDescription[i % 6]);
        }
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        //public final TextView textView;
        public final CardView cardView;
        public final ImageView imageView;
        public TextView textViewMovieTitle,textViewMovieDescription;

        public SimpleViewHolder(View view) {
            super(view);

            textViewMovieTitle = (TextView) view.findViewById(R.id.movie_title);
            textViewMovieDescription = (TextView) view.findViewById(R.id.movie_description);

            cardView = (CardView) view.findViewById(R.id.imagecardview);
            imageView = (ImageView) view.findViewById(R.id.itemImage);
            imageView.getLayoutParams().height = itemHeight;
            //imageView.getLayoutParams().width  = itemWidth;
            // bind focus listener
            view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        // run scale animation and make it bigger
                        cardView.setVisibility(View.INVISIBLE);
                    } else {
                        // run scale animation and make it smaller
                        // view.getLayoutParams().height = 200;
                        cardView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(this.context).inflate(resource, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {

        // Here you apply the animation when the view is bound
        // setAnimation(holder.itemView, position);
        // holder.textView.setText("Position :" + position);
        holder.imageView.setBackgroundResource(posterImageList.get(position));
        holder.textViewMovieTitle.setText(movieTitleList.get(position));
        holder.textViewMovieDescription.setText(context.getString(movieDescriptionList.get(position)));


        if(requestFocus && position==0) {
            holder.cardView.requestFocus();
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Click On Pos : " + position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.cardView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                System.out.println("Pos On : " + position);
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
        return position;
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}