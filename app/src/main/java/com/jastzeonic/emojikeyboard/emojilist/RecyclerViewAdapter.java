package com.jastzeonic.emojikeyboard.emojilist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastzeonic.emojikeyboard.R;

/**
 * The recyclerView mRecyclerViewContentAdapter of keyboard item
 * Created by Jast Lai on 2017/03/07.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    public interface OnItemClickListener {

        public boolean onItemClick(int position, View view);

    }


    protected Context context;

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    protected String[] items;

    protected RecyclerView mRecyclerView;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;

    public RecyclerViewAdapter(Context context, String[] items, RecyclerView mRecyclerView) {

        this.context = context;
        this.items = items;
        this.mRecyclerView = mRecyclerView;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_key, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.mTextViewContent.setText(items[position]);

    }

    @Override
    public int getItemCount() {

        return items == null ? 0 : items.length;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onClick(View view) {
        int itemPosition = mRecyclerView.getChildLayoutPosition(view);
        if (mOnItemClickListener.onItemClick(itemPosition, view)) {

        }

    }



}
