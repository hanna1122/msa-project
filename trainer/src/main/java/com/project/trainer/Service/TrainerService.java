package com.project.trainer.Service;

import com.project.trainer.domain.Trainers;
import com.project.trainer.dto.TrainerDto;
import com.project.trainer.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class TrainerService implements UserDetailsService {

    public final TrainerRepository trainerRepository;

    public final PasswordEncoder passwordEncoder;

    public Trainers signUp(TrainerDto trainerDto){
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
}
