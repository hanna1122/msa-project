package com.project.trainer.Service;

import com.project.trainer.domain.Lessons;
import com.project.trainer.domain.Record;
import com.project.trainer.dto.LessonDto;
import com.project.trainer.dto.RecordDto;
import com.project.trainer.repository.LessonRepository;
import com.project.trainer.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

    public final LessonRepository lessonRepository;

    public final RecordRepository recordRepository;

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
        return LessonDto.of(lesson);
    }

    public List<LessonDto> findLessons(){
        return lessonRepository.findAll()
                .stream()
                .map(LessonDto::of)
                .collect(Collectors.toList());
    }

    public Record saveRecord(RecordDto recordDto, String trainerId){

        return recordRepository.save(new Record(recordDto, trainerId));
    }

}
