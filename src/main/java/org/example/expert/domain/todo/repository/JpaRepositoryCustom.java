package org.example.expert.domain.todo.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JpaRepositoryCustom {
	Optional<Todo> findByIdWithUser(Long todoId);

	Page<Todo> findByKeywordAndModifiedAtAndManagers(String keyword, LocalDateTime startDateTime, LocalDateTime endDateTime, String managerNickname, Pageable pageable);
}
