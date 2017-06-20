package com.jastzeonic.emojikeyboard.emojilist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.WindowManager

import com.jastzeonic.emojikeyboard.R
import com.jastzeonic.emojikeyboard.database.EmojiTypeController
import com.jastzeonic.emojikeyboard.database.item.EmojiTypeItem
import com.jastzeonic.emojikeyboard.observer.EmojiObserver

/**
 * The recyclerView mRecyclerViewTypeAdapter of keyboard item
 * Created by Jast Lai on 2017/03/07.
 */

class RecyclerViewTypeAdapter(var context: Context, val mOnItemClickListener: OnItemTouchListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), EmojiObserver {


    var items: List<EmojiTypeItem>
    var displaymetrics: DisplayMetrics = DisplayMetrics()
    var isDisplaymetricsInit: Boolean = false
    var selectedPosition: Int = 0;


    init {
        items = getEmojiType()
        initSelection()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_key, parent, false)
        val width = getDisplayMetrics().widthPixels
        view.layoutParams.width = width / 3
        return ViewHolder(view, mOnItemClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder

        viewHolder.mTextViewContent.text = items[position].displayContent

        viewHolder.mTextViewContent.isSelected = items[position].isFocus


    }


    override fun getItemCount(): Int {

        return items.size
    }


    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    fun getDisplayMetrics(): DisplayMetrics {
        if (!isDisplaymetricsInit) {
            val windowManager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(displaymetrics)
            isDisplaymetricsInit = true
        }
        return displaymetrics;
    }

    fun notifySelectChange(newSelectPosition: Int) {
        items[selectedPosition].isFocus = false
        items[newSelectPosition].isFocus = true
        notifyItemChanged(selectedPosition)
        notifyItemChanged(newSelectPosition)
        selectedPosition = newSelectPosition

    }

    fun getEmojiType(): List<EmojiTypeItem> {
        val controller: EmojiTypeController = EmojiTypeController()
        return controller.allType
    }

    override fun update() {
        notifyItemRangeRemoved(0, items.size)
        items = getEmojiType()
        selectedPosition = 0
        initSelection()
        notifyItemRangeInserted(0, items.size)


    }

    fun initSelection() {
        for (emojiItemTypeItem in items) {
            emojiItemTypeItem.isFocus = false
        }
        items[selectedPosition].isFocus = true
    }

    fun getFirstItemTypeName(): String {
        if (items.isEmpty()) {
            return "happy"
        }
        return items.first().typeName
    }

    fun getItemTypeNameByPosition(position: Int): String {
        return items[position].typeName
    }

    fun getSelectedItemTypeName(): String {
        return items[selectedPosition].typeName
    }


}
