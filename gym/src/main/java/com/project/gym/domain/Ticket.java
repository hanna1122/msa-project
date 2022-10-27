package com.project.gym.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.gym.dto.TicketDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table(name = "TICKET")
@EntityListeners(AuditingEntityListener.class)
public class Ticket extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private Long lessonId;

    private String useYn;

    @Enumerated(EnumType.STRING)
    private UserType type;

    @Embedded
    private PersonalUser personalUser;

    @Embedded
    private GeneralUser generalUser;


    public static Ticket generalTicket(TicketDto ticketDto){
        return Ticket.builder()
                .userId(ticketDto.getUserId())
                .lessonId(ticketDto.getLessonId())
                .type(UserType.GENERAL)
                .useYn("Y")
                .generalUser(new GeneralUser(ticketDto.getStartDate(), ticketDto.getEndDate()))
                .build();
    }

    public static Ticket personalTicket(TicketDto ticketDto){
        return Ticket.builder()
                .userId(ticketDto.getUserId())
                .lessonId(ticketDto.getLessonId())
                .type(UserType.PERSONAL)
                .useYn("Y")
                .personalUser(new PersonalUser(ticketDto.getCount()))
                .build();
    }


    public void changeCount(Long count) {

        this.personalUser = new PersonalUser(count);
    }
}
