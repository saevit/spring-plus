package org.example.expert.domain.log.service;

import org.example.expert.domain.log.entitiy.Log;
import org.example.expert.domain.log.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {

	private final LogRepository logRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveManagerLog (Long todoId, Long userId) {
		Log log = new Log(todoId, userId);
		logRepository.save(log);
	}
}
