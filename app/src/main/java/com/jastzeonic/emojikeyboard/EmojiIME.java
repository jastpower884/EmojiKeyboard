package com.jastzeonic.emojikeyboard;

import android.inputmethodservice.AbstractInputMethodService;
import android.inputmethodservice.InputMethodService;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * The Emoji IME controller
 * <p>
 * Created by Jast Lai on 2017/03/07.
 */

public class EmojiIME extends InputMethodService {


    protected RecyclerView mRecyclerView;

    protected RecyclerViewAdapter adapter;

    protected String[] items;

    @Override
    public View onCreateInputView() {

        View view = getLayoutInflater().inflate(R.layout.layout_input_method_editor, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        items = getResources().getStringArray(R.array.happy);


        adapter = new RecyclerViewAdapter(this, items, mRecyclerView);
        adapter.setOnItemClickListener(onItemClickListener);

        mRecyclerView.setAdapter(adapter);

        return view;
    }


    protected RecyclerViewAdapter.OnItemClickListener onItemClickListener = new RecyclerViewAdapter.OnItemClickListener() {
        @Override
        public boolean onItemClick(int position, View view) {

            getCurrentInputConnection().commitText(items[position], 1);

            return false;
        }
    };
}
