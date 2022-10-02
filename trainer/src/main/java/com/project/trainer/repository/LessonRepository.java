package com.project.trainer.repository;

import com.project.trainer.domain.Lessons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository  extends JpaRepository<Lessons, Long> {

}
