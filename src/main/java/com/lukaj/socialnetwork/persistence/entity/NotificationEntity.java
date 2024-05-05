package com.lukaj.socialnetwork.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification")
public class NotificationEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "sender_id", foreignKey = @ForeignKey(name = "FK__NOTIFICATION__SENDER_ID"))
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", foreignKey = @ForeignKey(name = "FK__NOTIFICATION__RECEIVER_ID"))
    private UserEntity receiver;

    @ManyToOne
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK__NOTIFICATION__POST_ID"))
    private PostEntity post;

    @Column(name = "content")
    private String content;

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
