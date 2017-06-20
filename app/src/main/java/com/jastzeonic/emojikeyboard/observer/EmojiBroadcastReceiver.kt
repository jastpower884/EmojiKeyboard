package com.jastzeonic.emojikeyboard.observer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

/**
 * Created by Jast Lai on 2017/6/20.
 */

class EmojiBroadcastReceiver : BroadcastReceiver(), EmojiSubject {

    var listObserver: LinkedList<EmojiObserver> = LinkedList()

    override fun removeObserver(emojiObserver: EmojiObserver) {
        listObserver.remove(emojiObserver)
    }

    override fun notifyObservers() {
        for (emojiObserver in listObserver) {
            emojiObserver.update()
        }
    }

    override fun registerObserver(emojiObserver: EmojiObserver) {

        listObserver.add(emojiObserver)

    }

    override fun onReceive(context: Context?, intent: Intent?) {
        notifyObservers()
    }

}