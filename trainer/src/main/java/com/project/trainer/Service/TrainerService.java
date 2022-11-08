package com.project.trainer.Service;

import com.project.trainer.domain.Performance;
import com.project.trainer.domain.Trainers;
import com.project.trainer.dto.LessonDto;
import com.project.trainer.dto.PerformanceDto;
import com.project.trainer.dto.TrainerDto;
import com.project.trainer.exception.CustomException;
import com.project.trainer.repository.PerformanceRepository;
import com.project.trainer.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerService implements UserDetailsService {

    public final TrainerRepository trainerRepository;

    public final PerformanceRepository performanceRepository;

    public final PasswordEncoder passwordEncoder;

    public Trainers signUp(TrainerDto trainerDto){

        this.validateDuplicateUserId(trainerDto.getUserId());

        TrainerDto dto = TrainerDto.builder()
                .userId(trainerDto.getUserId())
                .password(passwordEncoder.encode(trainerDto.getPassword()))
                .name(trainerDto.getName())
                .build();

        return trainerRepository.save(new Trainers(dto));
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Trainers trainer = trainerRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(trainer.getUserId(), trainer.getPassword(),
                true, true, true, true,
                Collections.emptyList()
        );
    }

    public void validateDuplicateUserId(String userId) {
        if (trainerRepository.existsByUserId(userId)) {
            throw new DuplicateKeyException("userId");
        }
    }
    public Trainers getTrainer(Long trainerId) {
        return trainerRepository.findById(trainerId)
                .orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "trainer not found."));
    }


    public List<PerformanceDto> getPerformances(Long trainerId) {
        return performanceRepository.findAll()
                .stream()
                .map(PerformanceDto::of)
                .collect(Collectors.toList());
    }

    public void deleteTrainer(Long trainerId){
        trainerRepository.deleteById(trainerId);
    }
}
