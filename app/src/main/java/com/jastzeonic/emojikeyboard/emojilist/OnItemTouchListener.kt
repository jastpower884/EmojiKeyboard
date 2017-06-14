package com.jastzeonic.emojikeyboard.emojilist

import android.view.MotionEvent
import android.view.View

/**
 * Created by ptc_02008 on 2017/6/14.
 */
interface OnItemTouchListener {
    fun onItemClick(position: Int, view: View, event: MotionEvent): Boolean

}
