package com.project.trainer.repository;

import com.project.trainer.domain.Performance;
import com.project.trainer.domain.Trainers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
}
