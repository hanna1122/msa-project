package com.project.gym.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class GeneralUser {

    private LocalDate startDate;

    private LocalDate endDate;


}
