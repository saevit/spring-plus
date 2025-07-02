package org.example.expert.domain.todo.repository;

import static org.example.expert.domain.manager.entity.QManager.*;
import static org.example.expert.domain.todo.entity.QTodo.*;
import static org.example.expert.domain.user.entity.QUser.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaRepositoryCustomImpl implements JpaRepositoryCustom{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<Todo> findByIdWithUser(Long todoId) {
		Todo result = jpaQueryFactory
			.selectFrom(todo)
			.leftJoin(todo.user, user).fetchJoin()
			.where(todo.id.eq(todoId))
			.fetchOne();

		return Optional.ofNullable(result);
	}

	@Override
	public Page<Todo> findByKeywordAndModifiedAtAndManagers(String keyword, LocalDateTime startDateTime,
		LocalDateTime endDateTime, String managerNickname, Pageable pageable) {

		BooleanBuilder builder = new BooleanBuilder();

		// 제목 부분 검색
		if (keyword != null && !keyword.isEmpty()) {
			builder.and(todo.title.containsIgnoreCase(keyword));
		}

		// 생성일 범위
		if (startDateTime != null) {
			builder.and(todo.createdAt.goe(startDateTime));
		}
		if (endDateTime != null) {
			builder.and(todo.createdAt.loe(endDateTime));
		}

		// 담당자 닉네임 부분 검색 (Manager -> User 조인 필요)
		if (managerNickname != null && !managerNickname.isEmpty()) {
			builder.and(todo.managers.any().user.nickname.containsIgnoreCase(managerNickname));
		}

		// 조회 쿼리
		List<Todo> result = jpaQueryFactory
			.selectFrom(todo)
			.leftJoin(todo.managers, manager).fetchJoin()
			.leftJoin(manager.user, user).fetchJoin()
			.where(builder)
			.orderBy(todo.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 전체 개수 쿼리
		long total = jpaQueryFactory
			.select(todo.count())
			.from(todo)
			.leftJoin(todo.managers, manager)
			.leftJoin(manager.user, user)
			.where(builder)
			.fetchOne();

		return new PageImpl<>(result, pageable, total);
	}
}
