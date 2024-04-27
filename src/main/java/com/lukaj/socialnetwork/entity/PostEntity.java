package com.lukaj.socialnetwork.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "post")
public class PostEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "FK__POST__AUTHOR_ID"))
    private UserEntity author;

    @Column(name = "image_url")
    private String imageUrl;

    @NotBlank(message = "Description is required.")
    @Column(name = "content")
    private String content;

    @Column(name = "enable_comments")
    private Boolean enableComments;

    @OneToMany(mappedBy = "post")
    private Set<NotificationEntity> notifications;

    @OneToMany(mappedBy = "post")
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "post")
    private Set<LikeEntity> likes;

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getEnableComments() {
        return enableComments;
    }

    public void setEnableComments(Boolean enableComments) {
        this.enableComments = enableComments;
    }

    public Set<NotificationEntity> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<NotificationEntity> notifications) {
        this.notifications = notifications;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    public Set<LikeEntity> getLikes() {
        return likes;
    }

    public void setLikes(Set<LikeEntity> likes) {
        this.likes = likes;
    }
}
