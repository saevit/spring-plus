package org.example.expert.domain.log.entitiy;

import java.time.LocalDateTime;

import org.example.expert.domain.common.entity.Timestamped;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "logs")
public class Log extends Timestamped {

	@Id @GeneratedValue
	private Long id;

	private Long todoId;
	private Long userId;

	public Log (Long todoId, Long userId) {
		this.todoId = todoId;
		this.userId = userId;
	}
}
