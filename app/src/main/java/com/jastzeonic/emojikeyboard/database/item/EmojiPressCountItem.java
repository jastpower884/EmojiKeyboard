package com.jastzeonic.emojikeyboard.database.item;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ptc_02008 on 2017/6/13.
 */
@Entity
public class EmojiPressCountItem {

    @Id
    private Long id;
    private Long emojiId;
    private String content;
    @Generated(hash = 282949736)
    public EmojiPressCountItem(Long id, Long emojiId, String content) {
        this.id = id;
        this.emojiId = emojiId;
        this.content = content;
    }
    @Generated(hash = 1328922864)
    public EmojiPressCountItem() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getEmojiId() {
        return this.emojiId;
    }
    public void setEmojiId(Long emojiId) {
        this.emojiId = emojiId;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }


}
