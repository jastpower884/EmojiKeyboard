package com.jastzeonic.emojikeyboard.database.item;

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

    private String displayContent;

    private String typeName;

    @Transient
    private boolean focus;


    @Generated(hash = 1876295260)
    public EmojiTypeItem(Long id, String displayContent, String typeName) {
        this.id = id;
        this.displayContent = displayContent;
        this.typeName = typeName;
    }

    @Generated(hash = 364141089)
    public EmojiTypeItem() {
    }


    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayContent() {
        return this.displayContent;
    }

    public void setDisplayContent(String displayContent) {
        this.displayContent = displayContent;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}


