package com.jastzeonic.emojikeyboard.database;

import com.jastzeonic.emojikeyboard.database.item.EmojiItem;
import com.jastzeonic.emojikeyboard.database.item.EmojiItemDao;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

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


    public List<EmojiItem> getAllItem() {
        return emojiItemDao.loadAll();
    }

    public QueryBuilder<EmojiItem> getQueryBuilder() {
        return emojiItemDao.queryBuilder();
    }


    public List<EmojiItem> selectItemByItem(String... whereString) {
        QueryBuilder<EmojiItem> queryBuilder = getQueryBuilder();
        WhereCondition whereType = EmojiItemDao.Properties.Type.eq(whereString[0]);
        return queryBuilder.where(whereType).list();

    }

}
