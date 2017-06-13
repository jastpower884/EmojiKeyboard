package com.jastzeonic.emojikeyboard.emojilist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * The ViewHolder of keyboard item
 * Created by Jast Lai on 2017/03/07.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    protected TextView mTextViewContent;

    public ViewHolder(View itemView) {
        super(itemView);
        mTextViewContent = (TextView) itemView;

    }
}
