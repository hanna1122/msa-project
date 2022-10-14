package com.project.trainer.repository;

import com.project.trainer.domain.Record;
import com.project.trainer.domain.Trainers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
