package com.finsmart.core.common.outbox.repositories;

import com.finsmart.core.common.outbox.entities.OutboxEvent;
import com.finsmart.core.common.outbox.entities.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {

    List<OutboxEvent> findAllByStatus(OutboxStatus status);
}