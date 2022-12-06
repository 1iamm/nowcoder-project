package com.nowcoder.community.entity;

import org.apache.kafka.common.protocol.types.Field;

import javax.print.attribute.HashPrintJobAttributeSet;
import java.util.HashMap;
import java.util.Map;

public class Event {

    private String topic;//触发的事件
    private int userId; //触发事件的人
    private int entityType;
    private int entityId;
    private int entityUserId;//实体的作者（帖子、评论的作者）
    private Map<String, Object> data = new HashMap<>();

    public String getTopic() {
        return topic;
    }

    // set返回this，这样可以多次使用，如event.setA().setB().setC()...，非常实用！
    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public Event setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public Event setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Event setData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
}
