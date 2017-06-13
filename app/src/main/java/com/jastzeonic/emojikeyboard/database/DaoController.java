package com.jastzeonic.emojikeyboard.database;

import android.content.Context;

import com.jastzeonic.emojikeyboard.item.DaoMaster;
import com.jastzeonic.emojikeyboard.item.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Jast Lai on 2017/6/13.
 */

public class DaoController {

    private static DaoController daoController;

    public static DaoController getInstance() {
        if (daoController == null) {
            daoController = new DaoController();
        }
        return daoController;
    }

    private DaoSession daoSession;

    public void init(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "notes-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }


    public DaoSession getDaoSession() {
        return daoSession;
    }
}
