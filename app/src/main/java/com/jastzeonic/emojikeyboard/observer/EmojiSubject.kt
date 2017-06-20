package com.jastzeonic.emojikeyboard.observer

/**
 * Created by Jast Lai on 2017/6/20.
 */
interface EmojiSubject {



    fun registerObserver(emojiObserver: EmojiObserver)
    fun removeObserver(emojiObserver: EmojiObserver)
    fun notifyObservers()
}