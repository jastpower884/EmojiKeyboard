package com.jastzeonic.emojikeyboard.database;

import com.jastzeonic.emojikeyboard.item.EmojiItemDao;

/**
 * Created by ptc_02008 on 2017/6/13.
 */

public class EmojiItemController {

    private EmojiItemDao emojiItemDao;

    public EmojiItemController() {
        emojiItemDao = DaoController.getInstance().getDaoSession().getEmojiItemDao();
    }


    public EmojiItemDao getEmojiItemDao() {
        return emojiItemDao;
    }
}
