package org.example.expert.domain.todo.dto.response;

import java.time.LocalDateTime;

import org.example.expert.domain.user.dto.response.UserResponse;

import lombok.Getter;

@Getter
public class TodoSearchResponse {

	private final Long id;
	private final String title;
	private final int commentsNum;
	private final int managersNum;

	public TodoSearchResponse(Long id, String title, int commentsNum, int managersNum) {
		this.id = id;
		this.title = title;
		this.commentsNum = commentsNum;
		this.managersNum = managersNum;

	}
}
