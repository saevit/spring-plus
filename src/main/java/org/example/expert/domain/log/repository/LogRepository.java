package org.example.expert.domain.log.repository;

import org.example.expert.domain.log.entitiy.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
