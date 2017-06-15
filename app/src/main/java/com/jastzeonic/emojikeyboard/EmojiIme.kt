package com.jastzeonic.emojikeyboard

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import com.jastzeonic.emojikeyboard.database.EmojiItemController
import com.jastzeonic.emojikeyboard.database.EmojiTypeController

import com.jastzeonic.emojikeyboard.emojilist.RecyclerViewAdapter
import com.jastzeonic.emojikeyboard.emojilist.RecyclerViewTypeAdapter
import com.jastzeonic.emojikeyboard.database.item.EmojiItem
import com.jastzeonic.emojikeyboard.database.item.EmojiTypeItem
import com.jastzeonic.emojikeyboard.emojilist.OnItemTouchListener


/**
 * The Emoji IME controller
 *
 *
 * Created by Jast Lai on 2017/03/07.
 */

class EmojiIme : InputMethodService() {

    lateinit var emojiContentItems: List<EmojiItem>
    lateinit var recyclerViewEmojiType: RecyclerView
    lateinit var recyclerViewEmojiContent: RecyclerView
    lateinit var imageViewBackSpace: ImageView
    lateinit var imageViewClearLine: ImageView
    lateinit var textViewSpaceBar: TextView
    lateinit var imageViewGlobal: ImageView
    lateinit var textViewComma: TextView
    lateinit var adapterType: RecyclerViewTypeAdapter
    lateinit var adapterContent: RecyclerViewAdapter
    val emojiItemTypeItems: List<EmojiTypeItem> = getEmojiType()

    override fun onCreateInputView(): View {

        val view = layoutInflater.inflate(R.layout.layout_input_method_editor, null)
        initView(view)
        recyclerViewEmojiType.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewEmojiType.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL))

        recyclerViewEmojiContent.layoutManager = GridLayoutManager(this, 3)


        if (emojiItemTypeItems.isNotEmpty()) {
            emojiItemTypeItems.first().isFocus = true

            for (emojiItemTypeItem in emojiItemTypeItems) {
                emojiItemTypeItem.isFocus = false
            }
            emojiContentItems = getEmojiContent(emojiItemTypeItems.first().typeName)
        } else {
            emojiContentItems = getEmojiContent("angry")
        }
        adapterType = RecyclerViewTypeAdapter(this, emojiItemTypeItems, onTypeItemTouchListener)
        adapterContent = RecyclerViewAdapter(this, emojiContentItems, onItemTouchListener)





        recyclerViewEmojiContent.adapter = adapterContent
        recyclerViewEmojiType.adapter = adapterType

        return view
    }


    internal var onButtonClickListener: View.OnClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.image_view_back_space -> currentInputConnection.deleteSurroundingText(1, 1)
            R.id.image_view_clear_line -> currentInputConnection.deleteSurroundingText(maxWidth, 0)
            R.id.text_view_space_bar -> currentInputConnection.commitText(" ", 1)
            R.id.image_view_global -> {
                val imm = this@EmojiIme.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                try {
                    val token = this@EmojiIme.window.window!!.attributes.token
                    imm.switchToLastInputMethod(token)
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                    imm.showInputMethodPicker()
                }

            }
            R.id.text_view_comma -> currentInputConnection.commitText(getString(R.string.comma), 1)
        }
    }

    var onButtonLongClickListener: View.OnLongClickListener = View.OnLongClickListener {
        val imm = this@EmojiIme.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showInputMethodPicker()
        true
    }


    var touchEventTime: Long = 0L
    var touchFlag: Boolean = false

    val repeatPressStartTime: Long = 300L
    val repeatPressDelayTime: Long = 50L


    var onItemTouchListener: OnItemTouchListener = object : OnItemTouchListener {
        override fun onItemClick(position: Int, view: View, event: MotionEvent): Boolean {

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    touchEventTime = System.currentTimeMillis()
                    touchFlag = true
                    view.postDelayed(object : Runnable {
                        override fun run() {
                            if (touchFlag && System.currentTimeMillis() - repeatPressStartTime > touchEventTime) {
                                currentInputConnection.commitText(emojiContentItems[position].content, 1)
                                view.postDelayed(this, repeatPressDelayTime)
                            } else if (touchFlag) {
                                view.postDelayed(this, repeatPressDelayTime)
                            }
                        }
                    }, repeatPressDelayTime)
                }
                MotionEvent.ACTION_UP -> {
                    currentInputConnection.commitText(emojiContentItems[position].content, 1)
                    touchFlag = false
                }
                MotionEvent.ACTION_CANCEL -> touchFlag = false
                else -> {
                    Log.v("EmojiIme", "event.action:" + event.action)
                    return false
                }

            }
            return false;
        }

    }


    internal var onTypeItemTouchListener: OnItemTouchListener = object : OnItemTouchListener {
        override fun onItemClick(position: Int, view: View, event: MotionEvent): Boolean {

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    touchFlag = true

                }
                MotionEvent.ACTION_UP -> {
                    arrayListOf(emojiContentItems).clear()
                    adapterContent.notifyItemRangeRemoved(0, emojiContentItems.size)
                    emojiContentItems = getEmojiContent(emojiItemTypeItems[position].typeName)
                    adapterContent.setitems(emojiContentItems)
                    adapterContent.notifyItemRangeInserted(0, emojiContentItems.size)

//                    adapterContent = RecyclerViewAdapter(this@EmojiIme, emojiContentItems, onItemTouchListener)

                    adapterType.notifySelectChange(position)
                    touchFlag = false
                }
                MotionEvent.ACTION_CANCEL -> touchFlag = false
                else -> {
                    Log.v("EmojiIme", "event.action:" + event.action)
                    return false
                }

            }
            return false;
        }

    }


    val onButtonTouchEventListener: View.OnTouchListener = View.OnTouchListener { view, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchEventTime = System.currentTimeMillis()
                touchFlag = true
                view.postDelayed(object : Runnable {
                    override fun run() {
                        if (touchFlag && System.currentTimeMillis() - repeatPressStartTime > touchEventTime) {
                            onButtonClickListener.onClick(view)
                            view.postDelayed(this, repeatPressDelayTime)
                        } else if (touchFlag) {
                            view.postDelayed(this, repeatPressDelayTime)
                        }
                    }
                }, repeatPressDelayTime)
            }
            MotionEvent.ACTION_UP -> {
                onButtonClickListener.onClick(view)
                touchFlag = false
            }
            MotionEvent.ACTION_CANCEL -> touchFlag = false
            else -> {
                Log.v("EmojiIme", "event.action:" + event.action)
                return@OnTouchListener false
            }

        }
        false
    }


    private fun initView(rootView: View) {
        recyclerViewEmojiType = rootView.findViewById(R.id.recycler_view_emoji_class) as RecyclerView
        recyclerViewEmojiContent = rootView.findViewById(R.id.recycler_view_emoji_content) as RecyclerView
        imageViewBackSpace = rootView.findViewById(R.id.image_view_back_space) as ImageView
        imageViewClearLine = rootView.findViewById(R.id.image_view_clear_line) as ImageView
        textViewSpaceBar = rootView.findViewById(R.id.text_view_space_bar) as TextView
        imageViewGlobal = rootView.findViewById(R.id.image_view_global) as ImageView
        textViewComma = rootView.findViewById(R.id.text_view_comma) as TextView


        imageViewBackSpace.setOnTouchListener(onButtonTouchEventListener)
        imageViewClearLine.setOnTouchListener(onButtonTouchEventListener)
        textViewSpaceBar.setOnTouchListener(onButtonTouchEventListener)
        imageViewGlobal.setOnClickListener(onButtonClickListener)
        imageViewGlobal.setOnLongClickListener(onButtonLongClickListener)
        textViewComma.setOnTouchListener(onButtonTouchEventListener)
    }

    fun getEmojiContent(itemType: String): List<EmojiItem> {
        val controller: EmojiItemController = EmojiItemController()
        return controller.selectItemByItem(itemType)
    }

    fun getEmojiType(): List<EmojiTypeItem> {
        val controller: EmojiTypeController = EmojiTypeController()
        return controller.allType
    }


}
