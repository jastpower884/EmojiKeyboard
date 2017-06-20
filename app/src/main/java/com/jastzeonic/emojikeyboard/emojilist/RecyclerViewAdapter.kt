package com.jastzeonic.emojikeyboard.emojilist

import android.content.Context
import android.graphics.Point
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

import com.jastzeonic.emojikeyboard.R
import com.jastzeonic.emojikeyboard.database.item.EmojiItem
import android.opengl.ETC1.getWidth
import android.util.DisplayMetrics
import android.view.WindowManager
import com.jastzeonic.emojikeyboard.database.EmojiItemController
import com.jastzeonic.emojikeyboard.observer.EmojiObserver


/**
 * The recyclerView mRecyclerViewContentAdapter of keyboard item
 * Created by Jast Lai on 2017/03/07.
 */

class RecyclerViewAdapter(val context: Context, var emojiType: String, val mOnItemClickListener: OnItemTouchListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var items: List<EmojiItem>
    var displaymetrics: DisplayMetrics = DisplayMetrics()
    var isDisplaymetricsInit: Boolean = false


    init {
        items = getEmojiContent(emojiType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_key, parent, false)
        val width = getDisplayMetrics().widthPixels
        view.layoutParams.width = width / 3
        return ViewHolder(view, mOnItemClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        viewHolder.mTextViewContent.text = items[position].content
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

    fun getEmojiContent(itemType: String): List<EmojiItem> {
        val controller: EmojiItemController = EmojiItemController()
        return controller.selectItemByItem(itemType)
    }

    fun update() {
        notifyItemRangeRemoved(0, items.size)
        items = getEmojiContent(emojiType)
        notifyItemRangeInserted(0, items.size)


    }

    internal fun setEmojiType(emojiType: String): Unit {
        this.emojiType = emojiType
    }
}
