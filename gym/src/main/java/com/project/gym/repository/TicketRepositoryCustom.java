package com.project.gym.repository;

import com.project.gym.domain.Ticket;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.project.gym.domain.QTicket.ticket;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    public Ticket findTicket(Long ticketId){
        Ticket ticketInfo = (Ticket) queryFactory
                .select(ticket)
                .from(ticket)
                .where(
                        ticket.id.eq(ticketId),
                        ticket.personalUser.count.gt(0),
                        ticket.useYn.eq("Y")
                )
                .fetchOne();

        return ticketInfo;
    }

    @Transactional
    public void updateCount(Long ticketId, Long count){
        long execute = queryFactory
                .update(ticket)
                .set(ticket.personalUser.count, count)
                .where(ticket.id.eq(ticketId))
                .execute();
    }
}
