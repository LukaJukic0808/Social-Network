package com.lukaj.socialnetwork.rest.response;

public record PostResponse (
        Integer id,
        Integer authorId,
        String imageUrl,
        String content,
        Boolean enableComments,
        String createdAt,
        Integer notificationsRemaining,
        Integer postsRemaining,
        Integer userLikes,
        Integer userComments
) {
}
