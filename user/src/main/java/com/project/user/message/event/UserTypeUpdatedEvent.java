package com.project.user.message.event;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.user.domain.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class UserTypeUpdatedEvent {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("userType")
    private UserType userType;

    public UserTypeUpdatedEvent(String userId, UserType userType){
        this.userId = userId;
        this.userType = userType;
    }
}
