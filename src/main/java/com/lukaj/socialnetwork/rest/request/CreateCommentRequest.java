package com.lukaj.socialnetwork.rest.request;

public record CreateCommentRequest(Integer postId, String commentContent) {
}
