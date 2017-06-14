package com.jastzeonic.emojikeyboard.database;

import com.jastzeonic.emojikeyboard.database.item.EmojiTypeItem;
import com.jastzeonic.emojikeyboard.database.item.EmojiTypeItemDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by ptc_02008 on 2017/6/13.
 */

public class EmojiTypeController {

    private EmojiTypeItemDao emojiTypeItemDao;

    public EmojiTypeController() {
        emojiTypeItemDao = DaoController.getInstance().getDaoSession().getEmojiTypeItemDao();
    }


    public EmojiTypeItemDao getDao() {
        return emojiTypeItemDao;
    }


    public QueryBuilder<EmojiTypeItem> getQueryBuilder() {
        return emojiTypeItemDao.queryBuilder();
    }


    public List<EmojiTypeItem> getAllType() {
        return emojiTypeItemDao.loadAll();
    }


}
