package com.jastzeonic.emojikeyboard;

import android.app.Application;
import android.content.Intent;

import com.jastzeonic.emojikeyboard.database.DaoController;


public class EmojiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DaoController.getInstance().init(this);
        startService(new Intent(this, EmojiUpdateService.class));

    }
}
