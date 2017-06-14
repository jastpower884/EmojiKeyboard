package com.jastzeonic.emojikeyboard.emojilist

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView

/**
 * The ViewHolder of keyboard item
 * Created by Jast Lai on 2017/03/07.
 */

class ViewHolder(itemView: View, onTouchListener: OnItemTouchListener) : RecyclerView.ViewHolder(itemView) {

    var mTextViewContent: TextView = itemView as TextView

    init {
        mTextViewContent.setOnTouchListener { view, event -> onTouchListener.onItemClick(adapterPosition, view, event) }

    }
}
