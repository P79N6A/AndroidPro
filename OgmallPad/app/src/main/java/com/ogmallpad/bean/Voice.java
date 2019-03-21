package com.ogmallpad.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by root on 18-3-9.
 */

@Entity
public class Voice {
    //不能用int
    @Id(autoincrement = true)
    private Long id;

    //关键字
    private String path;

    //创建时间
    private Long time;

    //时长
    private Long timeSize;

    //客户id
    private Long customerId;

    private boolean isPlaying;

    @Generated(hash = 1853562457)
    public Voice(Long id, String path, Long time, Long timeSize, Long customerId,
            boolean isPlaying) {
        this.id = id;
        this.path = path;
        this.time = time;
        this.timeSize = timeSize;
        this.customerId = customerId;
        this.isPlaying = isPlaying;
    }

    @Generated(hash = 1158611544)
    public Voice() {
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getTimeSize() {
        return timeSize;
    }

    public void setTimeSize(Long timeSize) {
        this.timeSize = timeSize;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public boolean getIsPlaying() {
        return this.isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
}
