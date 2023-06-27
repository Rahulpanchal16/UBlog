package com.blog.services;

import com.blog.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(Integer postId, CommentDto commentDto);

	void deleteComment(Integer commentId);
}
