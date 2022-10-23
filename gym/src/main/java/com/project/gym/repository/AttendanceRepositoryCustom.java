package com.project.gym.repository;

import com.project.gym.domain.Attendance;
import com.project.gym.domain.Ticket;
import com.project.gym.dto.AttendanceDto;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

import static com.project.gym.domain.QAttendance.attendance;
import static com.project.gym.domain.QTicket.ticket;

@Repository
@RequiredArgsConstructor
public class AttendanceRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    DateTemplate<String> formattedRegDate = Expressions.dateTemplate(
            String.class
            , "DATE_FORMAT({0}, {1})"
            , attendance.regDate
            , ConstantImpl.create("%Y-%m-%d"));
    LocalDate today = LocalDate.now();

    DateTemplate<String> formattedStartDate = Expressions.dateTemplate(
            String.class
            , "DATE_FORMAT({0}, {1})"
            , ticket.generalUser.startDate
            , ConstantImpl.create("%Y-%m-%d"));

    DateTemplate<String> formattedEndDate = Expressions.dateTemplate(
            String.class
            , "DATE_FORMAT({0}, {1})"
            , ticket.generalUser.endDate
            , ConstantImpl.create("%Y-%m-%d"));

    String todayString = LocalDate.now().toString();
    LocalDate todayDate = LocalDate.now();

    public Optional<AttendanceDto> findPersonalAttendance(String userId){
        AttendanceDto attendanceDto =  queryFactory
                .select(Projections.fields(AttendanceDto.class, attendance.id.as("attendanceId"), ticket.id.as("ticketId"), ticket.userId.as("userId")))
                .from(ticket)
                .leftJoin(attendance).on(ticket.userId.eq(attendance.userId).and(formattedRegDate.eq(todayString)))
                .where(
                        ticket.userId.eq(userId),
                        ticket.useYn.eq("Y"),
                        ticket.personalUser.count.gt(0)
                )
                .fetchOne();
        return Optional.ofNullable(attendanceDto);
    }

    public Optional<AttendanceDto> findGeneralAttendance(String userId){
        AttendanceDto attendanceDto = queryFactory
                .select(Projections.fields(AttendanceDto.class, attendance.id.as("attendanceId"), ticket.id.as("ticketId"), ticket.userId.as("userId")))
                .from(ticket)
                .leftJoin(attendance).on(ticket.userId.eq(attendance.userId).and(formattedRegDate.eq(todayString)))
                .where(
                        ticket.userId.eq(userId),
                        ticket.useYn.eq("Y"),
                        ticket.generalUser.startDate.loe(todayDate),
                        ticket.generalUser.endDate.goe(todayDate)
                )
                .fetchOne();
        return Optional.ofNullable(attendanceDto);
    }


}
