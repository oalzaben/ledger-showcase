package com.example.ledger.payments;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    public record TransferRequest(UUID fromAccountId, UUID toAccountId, BigDecimal amount, String currency) {}

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest req, @RequestHeader(value = "idempotency-key", required = false) String idempotencyKey) {
        return ResponseEntity.accepted().body(Map.of(
                "status", "stub",
                "message", "Transfer logic will be implemented on Day 9",
                "idempotencyKey", idempotencyKey
        ));
    }
}
