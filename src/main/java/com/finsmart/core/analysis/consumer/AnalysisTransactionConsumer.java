package com.finsmart.core.analysis.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.finsmart.core.analysis.services.AnalysisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalysisTransactionConsumer {

    private final AnalysisService analysisService;

    @KafkaListener(topics = "finance-transactions", groupId = "finsmart-group")
    public void consumeTransactionEvent(JsonNode messagePayload) {
        log.info("Analysis modülüne yeni işlem düştü: {}", messagePayload.toString());

        try {

            String userIdStr = messagePayload.get("userId").asText();
            UUID userId = UUID.fromString(userIdStr);
            String transactionType = messagePayload.get("type").asText();
            BigDecimal amount  = new BigDecimal(messagePayload.get("amount").asText());

            analysisService.updateFinancialMetrics(userId, transactionType, amount);
        } catch (Exception e) {
            log.error("Analysis Tüketici tarafında Hata: {}", e);
        }
    }
}
