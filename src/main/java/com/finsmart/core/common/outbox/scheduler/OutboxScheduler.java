package com.finsmart.core.common.outbox.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finsmart.core.common.outbox.entities.OutboxEvent;
import com.finsmart.core.common.outbox.entities.OutboxStatus;
import com.finsmart.core.common.outbox.repositories.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxScheduler {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    public void processOutboxEvents() {
        List<OutboxEvent> pendingEvents = outboxEventRepository.findAllByStatus(OutboxStatus.PENDING);

        for (OutboxEvent event : pendingEvents) {
            try {
                Object payloadObject = objectMapper.readTree(event.getPayload());
                kafkaTemplate.send(event.getTopic(), event.getAggregateId(), payloadObject).get();
//                        .whenComplete((res, ex) -> {
//                            if (ex == null) {
//                                event.setStatus(OutboxStatus.PROCESSED);
//                                outboxEventRepository.save(event);
//                            } else {
//                                log.error("Kafka gönderimi başarısız: {}", event.getId(), ex);
//                            }
//                        }); //whenComplete, asenkron çalışmayı sağladığınan, kafka kapalıyken, gönderilmemiş bir veriyi
// birden fazla kafka'ya göndermenin önüne geçmek için iptal edildi, kafkaTemplate.send'in sonuna .get() eklendi.
// .get() metodu; "Kafka'ya mesajı at ve karşı taraftan onay gelene kadar burada bekle!" diyerek, işlemi senkron yaparak
// kafka kapalıyken arka arkaya asenkron çağrılar yapıp kafka'nın buffer'ını doldurmasını engeller.
                event.setStatus(OutboxStatus.PROCESSED);
                outboxEventRepository.save(event);
            } catch (Exception e) {
                log.error("Outbox event işlenirken hata alındı! ID: {}", event.getId(), e);
            }
        }
    }
}
