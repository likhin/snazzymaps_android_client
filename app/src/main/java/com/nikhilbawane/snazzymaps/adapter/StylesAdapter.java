package com.nikhilbawane.snazzymaps.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikhilbawane.snazzymaps.R;
import com.nikhilbawane.snazzymaps.model.Style;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecyclerView Adapter for map styles
 * <p>
 * Created by Nikhil on 07-08-2017.
 */

public class StylesAdapter extends RecyclerView.Adapter<StylesAdapter.StyleViewHolder> {

    private List<Style> styles;

    public StylesAdapter(List styles) {
        this.styles = styles;
    }

    @Override
    public StyleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.style_card, parent, false);
        return new StyleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StyleViewHolder holder, int position) {
        Style currentStyle = styles.get(position);
        holder.name.setText(currentStyle.getName());
        holder.createdBy.setText("by " + currentStyle.getCreatedBy().getName());
        holder.views.setText(currentStyle.getViews().toString());
        holder.favorites.setText(currentStyle.getFavorites().toString());
        holder.tags.setText(currentStyle.getTags().toString());
        holder.colors.setText(currentStyle.getColors().toString());
    }

    @Override
    public int getItemCount() {
        return styles.size();
    }

    class StyleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card) CardView cardView;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.views) TextView views;
        @BindView(R.id.favorites) TextView favorites;
        @BindView(R.id.created_by) TextView createdBy;
        @BindView(R.id.tags) TextView tags;
        @BindView(R.id.colors) TextView colors;

        public StyleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
