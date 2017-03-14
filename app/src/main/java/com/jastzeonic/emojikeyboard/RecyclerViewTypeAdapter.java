package com.jastzeonic.emojikeyboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastzeonic.emojikeyboard.item.TypeItem;

import java.util.List;

/**
 * The recyclerView mRecyclerViewTypeAdapter of keyboard item
 * Created by Jast Lai on 2017/03/07.
 */

public class RecyclerViewTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    public interface OnItemClickListener {

        public boolean onItemClick(int position, View view);

    }


    protected Context context;

    public List<TypeItem> getItems() {
        return items;
    }

    public void setItems(List<TypeItem> items) {
        this.items = items;
    }

    protected List<TypeItem> items;

    protected RecyclerView mRecyclerView;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;

    public RecyclerViewTypeAdapter(Context context, List<TypeItem> items, RecyclerView mRecyclerView) {

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

        viewHolder.mTextViewContent.setText(items.get(position).getContent());

        viewHolder.mTextViewContent.setSelected(items.get(position).isForcus());


    }

    @Override
    public int getItemCount() {

        return items == null ? 0 : items.size();
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
