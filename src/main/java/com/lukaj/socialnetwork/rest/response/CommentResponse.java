package com.lukaj.socialnetwork.rest.response;

public record CommentResponse(
        Integer id,
        Integer authorId,
        String authorUsername,
        String createdAt,
        String content
) {
}
