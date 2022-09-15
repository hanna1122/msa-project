package com.project.trainer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@Embeddable
public class Performance {
    public String performance;
    public int lessonCount;
}
