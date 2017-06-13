package com.jastzeonic.emojikeyboard.item;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Emoji's Item structure.
 * Created by Jast Lai on 2017/6/13.
 */
@Entity
public class EmojiItem {
    @Id
    private Long id;
    private String type;
    private String content;
    @Generated(hash = 651806061)
    public EmojiItem(Long id, String type, String content) {
        this.id = id;
        this.type = type;
        this.content = content;
    }
    @Generated(hash = 342449651)
    public EmojiItem() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }


}
