package com.project.trainer.Service;

import com.project.trainer.domain.Lessons;
import com.project.trainer.dto.LessonDto;
import com.project.trainer.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

    public final LessonRepository lessonRepository;

    public Lessons saveLesson(LessonDto lessonDto, String userId){
        LessonDto dto = LessonDto.builder()
                .trainerId(userId)
                .lessonName(lessonDto.getLessonName())
                .price(lessonDto.getPrice())
                .lessonType(lessonDto.getLessonType())
                .count(lessonDto.getCount())
                .startDate(lessonDto.getStartDate())
                .endDate(lessonDto.getEndDate())
                .build();
        return lessonRepository.save(new Lessons(dto));
    }

    public LessonDto getLesson(Long lessonId){
        Lessons lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        return LessonDto.create(lesson);
    }

    public List<LessonDto> findLessons(){
        return lessonRepository.findAll()
                .stream()
                .map(LessonDto::of)
                .collect(Collectors.toList());
    }

}
