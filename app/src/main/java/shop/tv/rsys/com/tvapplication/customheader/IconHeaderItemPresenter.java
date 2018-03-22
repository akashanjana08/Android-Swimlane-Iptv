package shop.tv.rsys.com.tvapplication.customheader;

/**
 * Created by Akash.Sharma on 12/6/2017.
 */
import android.content.Context;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.PageRow;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowHeaderPresenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import shop.tv.rsys.com.tvapplication.R;


public class IconHeaderItemPresenter extends RowHeaderPresenter {

    private float mUnselectedAlpha;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {

        mUnselectedAlpha = viewGroup.getResources()
                .getFraction(R.fraction.lb_browse_header_unselect_alpha, 1, 1);
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.icon_header_item, null);

        //view.setAlpha(mUnselectedAlpha); // Initialize icons to be at half-opacity.

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        HeaderItem headerItem = ((PageRow) item).getHeaderItem();
        View rootView = viewHolder.view;
        //rootView.setFocusable(true);

        /*ImageView iconView = (ImageView) rootView.findViewById(R.id.header_icon);
        Drawable icon = rootView.getResources().getDrawable(R.drawable.android_header, null);
        iconView.setImageDrawable(icon);*/

        TextView label = (TextView) rootView.findViewById(R.id.header_label);
        label.setText(headerItem.getName());
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        // no op
    }

    // TODO: This is a temporary fix. Remove me when leanback onCreateViewHolder no longer sets the
    // mUnselectAlpha, and also assumes the xml inflation will return a RowHeaderView.
    @Override
    protected void onSelectLevelChanged(RowHeaderPresenter.ViewHolder holder) {
        holder.view.setAlpha(mUnselectedAlpha + holder.getSelectLevel() *
                (1.0f - mUnselectedAlpha));
    }
}