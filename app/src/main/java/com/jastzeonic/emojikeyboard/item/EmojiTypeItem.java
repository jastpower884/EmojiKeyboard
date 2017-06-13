package com.jastzeonic.emojikeyboard.item;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Jast Lai on 09/03/2017.
 */
@Entity
public class EmojiTypeItem {

    @Id(autoincrement = true)
    private Long id;

    private String content;

    @Transient
    private boolean focus;

    @Generated(hash = 809407416)
    public EmojiTypeItem(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    @Generated(hash = 364141089)
    public EmojiTypeItem() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}


