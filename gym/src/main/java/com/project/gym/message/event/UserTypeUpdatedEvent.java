package com.project.gym.message.event;

import com.project.gym.domain.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class UserTypeUpdatedEvent {

    private String userId;
    private UserType userType;

    public UserTypeUpdatedEvent(String userId, UserType userType){
        this.userId = userId;
        this.userType = userType;
    }
}
