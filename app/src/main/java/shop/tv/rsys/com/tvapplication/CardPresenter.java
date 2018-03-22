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

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

/*
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an Image CardView
 */
public class CardPresenter extends Presenter {
    private static final String TAG = "CardPresenter";

    private static final int CARD_WIDTH = 500;
    private static final int CARD_HEIGHT = 200;
    private static Drawable sSelectedBackgroundColor;
    private static Drawable sDefaultBackgroundColor;
    private Drawable mDefaultCardImage;
    private static  Context mContext;

    public CardPresenter()
    {}

    public CardPresenter(Context mContext)
    {
        this.mContext= mContext;
    }

    private static void updateCardBackgroundColor(ImageCardView view, boolean selected) {
        Drawable color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        // Both background colors should be set because the view's background is temporarily visible
        // during animations.
        //view.setBackgroundColor(color);
        view.setBackground(ContextCompat.getDrawable(mContext, R.drawable.card_back_ground_color));
        //view.findViewById(R.id.info_field).setBackgroundColor(color);
        view.findViewById(R.id.info_field).setBackgroundDrawable(color);
    }

    private static void updateCardContextText(String text)
    {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG, "onCreateViewHolder");
        //sDefaultBackgroundColor  = parent.getResources().getColor(R.color.default_background);
        sDefaultBackgroundColor  = ContextCompat.getDrawable(mContext, R.drawable.back_ground);
        //sSelectedBackgroundColor = parent.getResources().getColor(R.color.selected_background);
        sSelectedBackgroundColor = ContextCompat.getDrawable(mContext, R.drawable.card_back_ground_color);
        mDefaultCardImage = parent.getResources().getDrawable(R.drawable.movie);

        final ImageCardView cardView = new ImageCardView(parent.getContext()) {
            @Override
            public void setSelected(boolean selected) {
                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }
        };
        //cardView.setInfoVisibility(BaseCardView.CARD_REGION_VISIBLE_SELECTED);
        cardView.clearFocus();
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        updateCardBackgroundColor(cardView, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        Movie movie = (Movie) item;
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.getMainImageView().setScaleType(ImageView.ScaleType.FIT_XY);


        Log.d(TAG, "onBindViewHolder");
        if (movie.getCardImageUrl() != null) {
            cardView.setTitleText(movie.getTitle());
            cardView.setContentText(movie.getStudio());
            cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
            Glide.with(viewHolder.view.getContext())
                    .load(movie.getCardImageUrl())
                    .override(500,200)
                    .fitCenter()
                    .error(mDefaultCardImage)
                    .dontTransform()
                    .into(cardView.getMainImageView());
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        // Remove references to images so that the garbage collector can free up memory
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }
}
