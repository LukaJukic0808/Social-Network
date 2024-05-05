package com.lukaj.socialnetwork.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "UQ__USERNAME", columnNames = "username"),
        @UniqueConstraint(name = "UQ__EMAIL", columnNames = "email")
})
public class UserEntity extends AbstractEntity {

    @NotBlank(message = "First name field cannot be blank.")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name field cannot be blank.")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Username field cannot be blank.")
    @Column(name = "username")
    private String username;

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$",
            message = "Password must have at least 8 characters, at least one lowercase and uppercase " +
                    "English letter and at least one digit.")
    @Column(name = "password")
    private String password;

    @Pattern(regexp = "[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}",
            message = "Invalid email. Example: name@example.com")
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private Set<NotificationEntity> notificationsSent;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<NotificationEntity> notificationsReceived;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<CommentEntity> comments;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<PostEntity> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<LikeEntity> likes;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<NotificationEntity> getNotificationsSent() {
        return notificationsSent;
    }

    public void setNotificationsSent(Set<NotificationEntity> notificationsSent) {
        this.notificationsSent = notificationsSent;
    }

    public List<NotificationEntity> getNotificationsReceived() {
        return notificationsReceived;
    }

    public void setNotificationsReceived(List<NotificationEntity> notificationsReceived) {
        this.notificationsReceived = notificationsReceived;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(Set<CommentEntity> comments) {
        this.comments = comments;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(List<PostEntity> posts) {
        this.posts = posts;
    }

    public Set<LikeEntity> getLikes() {
        return likes;
    }

    public void setLikes(Set<LikeEntity> likes) {
        this.likes = likes;
    }
}
